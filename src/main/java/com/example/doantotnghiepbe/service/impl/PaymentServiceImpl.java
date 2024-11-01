//package com.example.doantotnghiepbe.service.impl;
//
//import com.example.doantotnghiepbe.configurations.VNPayConfig;
//import com.example.doantotnghiepbe.dto.PaymentDTO;
//import com.example.doantotnghiepbe.entity.Payment;
//import com.example.doantotnghiepbe.entity.Post;
//import com.example.doantotnghiepbe.entity.Price;
//import com.example.doantotnghiepbe.repository.PaymentRepository;
//import com.example.doantotnghiepbe.repository.PostRepository;
//import com.example.doantotnghiepbe.repository.PriceRepository;
//import com.example.doantotnghiepbe.service.PaymentService;
//import jakarta.persistence.EntityNotFoundException;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    @Service
//    public class PaymentService {
//
//        @Autowired
//        private PaymentRepository paymentRepository;
//
//        @Autowired
//        private PriceRepository priceRepository;
//
//        public Payment processPaymentReturn(String txnRef, String transactionNo, String amount, String paymentStatus) {
//            // Tìm đối tượng Payment bằng txnRef nếu cần
//            Payment payment = paymentRepository.findByVnpTxnRef(txnRef);
//
//            // Nếu không tìm thấy payment với txnRef này, tạo mới
//            if (payment == null) {
//                payment = new Payment();
//                payment.setVnpTxnRef(txnRef);
//                payment.setVnpTransactionId(transactionNo);
//
//                // Giả sử amount được truyền dưới dạng số lượng nhỏ nhất của VND (nhân 100 nếu cần)
//                payment.setPaymentAmount(Long.parseLong(amount));
//                payment.setPaymentStatus(paymentStatus);
//                payment.setPaymentDate(LocalDateTime.now());
//
//                // Lưu thông tin khác như Price hoặc Post nếu cần, ví dụ:
//                Price price = priceRepository.findById(1).orElse(null); // lấy Price ID tùy vào logic
//                payment.setPriceId(price);
//
//                // Lưu vào cơ sở dữ liệu
//                paymentRepository.save(payment);
//            }
//
//            return payment;
//        }
//    }
//
//}
