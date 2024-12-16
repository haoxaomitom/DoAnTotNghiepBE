package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.configurations.VNPayConfig;
import com.example.doantotnghiepbe.dto.PaymentResDTO;
import com.example.doantotnghiepbe.dto.PaymentSuccessDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Price;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.PriceRepository;
import com.example.doantotnghiepbe.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentResDTO createPayment(HttpServletRequest req, Integer priceId, Integer postId) throws UnsupportedEncodingException {
        Price selectedPrice = priceRepository.findByPriceId(priceId);
        if (selectedPrice == null) throw new IllegalArgumentException("Price not found");

        Post post = postRepository.findByPostId(postId);
        if (post == null) throw new IllegalArgumentException("Post not found");

        Integer amount = selectedPrice.getAmount();
        Integer discountPercentage = Optional.ofNullable(selectedPrice.getDiscountPercentage()).orElse(0);
        Integer discountAmount = (amount * discountPercentage) / 100;
        int finalAmount = amount - discountAmount;

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);
        Map<String, String> vnp_Params = buildVnpParams(req, vnp_TxnRef, finalAmount, vnp_IpAddr);

        String queryUrl = buildQueryUrl(vnp_Params);
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        Payment payment = new Payment();
        payment.setPostId(post);
        payment.setPriceId(selectedPrice);
        payment.setVnpTxnRef(vnp_TxnRef);
        payment.setPaymentAmount(finalAmount);
        payment.setBankCode("NCB");
        payment.setPaymentStatus("Pending");
        payment.setPaymentInfo("Thanh toan don hang: " + vnp_TxnRef);
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        PaymentResDTO paymentResDTO = new PaymentResDTO("OK", "Successfully", paymentUrl);
        return paymentResDTO;
    }

    @Override
    public void handleVNPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String txnRef = request.getParameter("vnp_TxnRef");
        Payment payment = paymentRepository.findByVnpTxnRef(txnRef);
        if (payment == null) {
            response.sendRedirect("http://127.0.0.1:5500/app/components/payment/PaymentSuccess.html?status=failure");
            return;
        }

        if (validatePayment(request.getParameterMap(), request.getParameter("vnp_SecureHash"))) {
            updatePaymentStatus(payment, request.getParameter("vnp_ResponseCode"), request.getParameter("vnp_TransactionNo"));
            response.sendRedirect("http://127.0.0.1:5500/app/components/payment/PaymentSuccess.html?txnRef=" + txnRef);
        } else {
            response.sendRedirect("http://127.0.0.1:5500/app/components/payment/PaymentSuccess.html?status=failure");
        }
    }

    @Override
    public PaymentSuccessDTO getPaymentDetails(String txnRef) {
        Payment payment = paymentRepository.findByVnpTxnRef(txnRef);
        if (payment == null) throw new IllegalArgumentException("Payment not found");
        return modelMapper.map(payment, PaymentSuccessDTO.class);
    }

    @Override
    public List<Object[]> getRevenueByMonth(int year) {
        return paymentRepository.getRevenueByMonth(year);
    }

    private Map<String, String> buildVnpParams(HttpServletRequest req, String vnp_TxnRef, int finalAmount, String vnp_IpAddr) throws UnsupportedEncodingException {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(finalAmount * 100));
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
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));

        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));
        return vnp_Params;
    }

    private String buildQueryUrl(Map<String, String> vnp_Params) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII)).append('&');
            }
        }

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        query.append("vnp_SecureHash=").append(vnp_SecureHash);
        return query.toString();
    }

    private void updatePaymentStatus(Payment payment, String responseCode, String transactionNo) {
        if ("00".equals(responseCode)) {
            payment.setPaymentStatus("Success");
            payment.setVnpTransactionId(transactionNo);
            updateTopPostEndDate(payment);
        } else {
            payment.setPaymentStatus("Failed");
        }
        paymentRepository.save(payment);
    }

    private void updateTopPostEndDate(Payment payment) {
        Post post = payment.getPostId();
        Price price = payment.getPriceId();
        LocalDateTime currentDate = LocalDateTime.now();
        int duration = price.getDuration();

        if (post.getTopPostEnd() == null || post.getTopPostEnd().isBefore(currentDate)) {
            post.setTopPostEnd(currentDate.plusDays(duration));
        } else {
            post.setTopPostEnd(post.getTopPostEnd().plusDays(duration));
        }
        postRepository.save(post);
    }

    private boolean validatePayment(Map<String, String[]> params, String secureHash) {
        Map<String, String> vnp_Params = new HashMap<>();
        params.forEach((k, v) -> vnp_Params.put(k, v[0]));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (!"vnp_SecureHash".equals(fieldName) && fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(fieldValue).append('&');
            }
        }
        hashData.setLength(hashData.length() - 1);

        String calculatedHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        return calculatedHash.equals(secureHash);
    }
}
