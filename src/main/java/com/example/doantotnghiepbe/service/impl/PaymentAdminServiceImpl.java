package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PaymentAdminDTO;
import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.service.PaymentAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentAdminServiceImpl implements PaymentAdminService {

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public Page<PaymentAdminDTO> getAllPayments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Tạo đối tượng Pageable
        Page<Payment> paymentsPage = paymentRepository.findAll(pageable); // Sử dụng phân trang

        // Convert từng `Payment` sang `PaymentAdminDTO` và trả về Page<PaymentAdminDTO>
        return paymentsPage.map(this::convertToDTO);
    }


    @Override
    public PaymentAdminDTO getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById((long) id.intValue())
                .orElseThrow(() -> new RuntimeException("Payment not found!"));
        return convertToDTO(payment);
    }

    @Override
    public PaymentAdminDTO updatePaymentStatus(Integer id, String status) {
        Payment payment = paymentRepository.findById((long) id.intValue())
                .orElseThrow(() -> new RuntimeException("Payment not found!"));
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
        return convertToDTO(payment);
    }

    @Override
    public Page<Payment> getPaymentsByCriteria(Long userId, Long paymentId, Integer postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentRepository.findPaymentsByCriteria(userId, paymentId, postId, pageable);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    private PaymentAdminDTO convertToDTO(Payment payment) {
        PaymentAdminDTO dto = new PaymentAdminDTO();
        dto.setPaymentId(payment.getPaymentId());

        // Chuyển đổi thông tin từ Post
        if (payment.getPostId() != null) {
            dto.setPostId(payment.getPostId().getPostId());
            dto.setPostName(payment.getPostId().getParkingName()); // Giả sử Post có trường parkingName
            dto.setUserId(payment.getPostId().getUser().getUserId());
        }

        // Chuyển đổi thông tin từ Price
        if (payment.getPriceId() != null) {
            dto.setPriceId(payment.getPriceId().getPriceId());
            dto.setPriceAmount(payment.getPriceId().getAmount()); // Giả sử Price có trường priceAmount
        }

        dto.setPaymentAmount(payment.getPaymentAmount());
        dto.setPaymentInfo(payment.getPaymentInfo());
        dto.setCurrency(payment.getCurrency());
        dto.setBankCode(payment.getBankCode());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setTopPostEnd(payment.getTopPostEnd());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setVnpTxnRef(payment.getVnpTxnRef());
        dto.setVnpTransactionId(payment.getVnpTransactionId());

        return dto;
    }

}
