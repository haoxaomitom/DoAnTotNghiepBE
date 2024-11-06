package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.configurations.VNPayConfig;
import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Price;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.PriceRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/vnpay")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class PaymentController {

    @Autowired
    private PriceRepository priceRepository;

//    @Autowired
//    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/payment/{priceId}/{postId}")
    public ResponseEntity<?> createPayment(HttpServletRequest req,
                                           @PathVariable Integer priceId,
                                           @PathVariable Integer postId) throws UnsupportedEncodingException {

        // Fetch the price information by priceId
        Price selectedPrice = priceRepository.findByPriceId(priceId);
        if (selectedPrice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Price not found");
        }

        // Fetch the post information by postId
        Post post = postRepository.findByPostId(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        // Tính toán amount sau khi giảm giá
        Integer amount = selectedPrice.getAmount(); // Sử dụng amount từ Price entity
        Integer discountPercentage = selectedPrice.getDiscountPercentage() != null ? selectedPrice.getDiscountPercentage() : 0; // Handle possible null

        // Calculate discounted amount
        Integer discountAmount = (amount * discountPercentage) / 100;
        int finalAmount = amount - discountAmount; // Calculate final price after discount

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(finalAmount * 100)); // Convert to the smallest unit for VNPay
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_OrderType", VNPayConfig.vnp_OrderType);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        Payment payment = new Payment();
        payment.setPostId(post);
        payment.setPriceId(selectedPrice); // Set giá trị Price
        payment.setVnpTxnRef(vnp_TxnRef);
        payment.setPaymentAmount(finalAmount); // Use the finalAmount calculated
        payment.setBankCode("NCB");
        payment.setPaymentStatus("Pending");
        payment.setPaymentInfo("Thanh toan don hang: " + vnp_TxnRef);
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("0k");
        paymentResDTO.setMessage("Successfully");
        paymentResDTO.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }

    @GetMapping("/return")
    public void handleVNPayReturn(@RequestParam Map<String, String> allParams, HttpServletResponse response) throws IOException {
        String txnRef = allParams.get("vnp_TxnRef");
        Payment payment = paymentRepository.findByVnpTxnRef(txnRef);

        if (payment != null) {
            Map<String, String> vnp_Params = new HashMap<>(allParams);
            vnp_Params.remove("vnp_SecureHash"); // Remove vnp_SecureHash for hash calculation
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            for (String fieldName : fieldNames) {
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    hashData.append(fieldName).append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    hashData.append('&');
                }
            }
            if (hashData.length() > 0) {
                hashData.setLength(hashData.length() - 1); // Remove the last '&'
            }

            // Validate secure hash
            if (VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString()).equals(allParams.get("vnp_SecureHash"))) {
                String paymentStatus = allParams.get("vnp_ResponseCode");
                if ("00".equals(paymentStatus)) { // "00" is the success code
                    payment.setPaymentStatus("Success");
                    payment.setVnpTransactionId(allParams.get("vnp_TransactionNo"));
                    LocalDateTime paymentDate = LocalDateTime.now();
                    payment.setPaymentDate(paymentDate);

                    // Retrieve the Price and Post entities
                    Price price = priceRepository.findByPriceId(payment.getPriceId().getPriceId());
                    Post post = payment.getPostId(); // Assuming Post is linked to Payment

                    if (price != null && post != null) {
                        int duration = price.getDuration(); // Duration in days
                        LocalDateTime currentTopPostEnd = post.getTopPostEnd();
                        LocalDateTime currentDate = LocalDateTime.now();

                        // Check if top_post_end is expired or null
                        if (currentTopPostEnd == null || currentTopPostEnd.isBefore(currentDate)) {
                            // Set new top_post_end based on current date
                            post.setTopPostEnd(currentDate.plusDays(duration));
                            payment.setTopPostEnd(currentDate.plusDays(duration));
                        } else {
                            // Extend top_post_end by adding duration days
                            post.setTopPostEnd(currentTopPostEnd.plusDays(duration));
                            payment.setTopPostEnd(currentDate.plusDays(duration));
                        }

                        // Save updated post and payment entities
                        postRepository.save(post);
                        paymentRepository.save(payment);

                        // Redirect to success page with txnRef as a query parameter
                        response.sendRedirect("http://127.0.0.1:5500/app/components/payment/PaymentSuccess.html?txnRef=" + txnRef);
                        return;
                    }
                } else {
                    // Update status as Failed if payment was not successful
                    payment.setPaymentStatus("Failed");
                    paymentRepository.save(payment);

                    // Redirect to failure page
                    response.sendRedirect("http://127.0.0.1:5500/app/components/payment/PaymentSuccess.html?txnRef=" + txnRef);
                    return;
                }
            }
        }
        // Redirect to failure page if payment is null or secure hash is invalid
        response.sendRedirect("http://127.0.0.1:5500/app/components/payment/PaymentSuccess.html?status=failure");
    }



    @GetMapping("/payment/details/{txnRef}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable String txnRef) {
        Payment payment = paymentRepository.findByVnpTxnRef(txnRef);

        if (payment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }

        // Use ModelMapper to map the Payment entity to PaymentSuccessDTO
        PaymentSuccessDTO paymentSuccessDTO = modelMapper.map(payment, PaymentSuccessDTO.class);

        return ResponseEntity.ok(paymentSuccessDTO);
    }
}
