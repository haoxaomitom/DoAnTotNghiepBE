package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.configurations.VNPayConfig;
import com.example.doantotnghiepbe.dto.PaymentDTO;
import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Price;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.PriceRepository;
import com.example.doantotnghiepbe.service.PaymentService;
import com.example.doantotnghiepbe.service.PriceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/vnpay")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PaymentController {

    @Autowired
    private PriceRepository priceRepository;

//    @Autowired
//    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/payment/{priceId}")
    public ResponseEntity<?> createPayment(HttpServletRequest req,
                                           @PathVariable Integer priceId) throws UnsupportedEncodingException {

        Price selectedPrice = priceRepository.findByPriceId(priceId);
        if (selectedPrice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Price not found");
        }
        long amount = selectedPrice.getAmount() * 100;  // Nhân với 100 nếu VNPay yêu cầu VND nhỏ nhấ

//        long amount = 1000000 * 100 ;

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
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

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
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

        Price price = priceRepository.findByPriceId(priceId);
        if (price == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Price not found");
        }

        Post post = postRepository.findByPostId(1); // Lấy post có ID = 1
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        Payment payment = new Payment();
        payment.setPostId(post);
        payment.setPriceId(price);
        payment.setVnpTxnRef(vnp_TxnRef);
        payment.setPaymentAmount(amount);
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
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> allParams) {
        String txnRef = allParams.get("vnp_TxnRef");
        Payment payment = paymentRepository.findByVnpTxnRef(txnRef);

        if (payment != null) {
            // Tạo lại hashData cho các tham số nhận được từ VNPay
            Map<String, String> vnp_Params = new HashMap<>(allParams);
            vnp_Params.remove("vnp_SecureHash"); // Loại bỏ tham số vnp_SecureHash
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
                hashData.setLength(hashData.length() - 1); // Xóa ký tự '&' cuối cùng
            }

            // Kiểm tra SecureHash
            if (VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString()).equals(allParams.get("vnp_SecureHash"))) {
                payment.setPaymentStatus("Success");
                payment.setVnpTransactionId(allParams.get("vnp_TransactionNo"));
                payment.setPaymentDate(LocalDateTime.now());

                // Lấy thông tin duration từ bảng Price
                Price price = priceRepository.findByPriceId(payment.getPriceId().getPriceId());
                if (price != null) {
                    int duration = price.getDuration(); // Giả sử duration là kiểu int
                    LocalDateTime topPostEnd = LocalDateTime.now().plusDays(duration);
                    payment.setTopPostEnd(topPostEnd); // Set ngày hết hạn
                }

                paymentRepository.save(payment);
                return ResponseEntity.ok("Payment successful");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment data");
    }

}
