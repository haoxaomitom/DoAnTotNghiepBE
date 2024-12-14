package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.dto.PaymentUserDTO;
import com.example.doantotnghiepbe.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByVnpTxnRef(String vnpTxnRef);

    @Query("SELECT p FROM Payment p WHERE p.postId.user.userId = :userId")
    Page<Payment> findPaymentsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Payment p " +
            "JOIN p.postId post " +
            "WHERE post.user.userId = :userId " +
            "AND (:paymentId IS NULL OR p.paymentId = :paymentId) " +
            "AND (:postId IS NULL OR post.postId = :postId)")
    Page<Payment> findPaymentsByCriteria(Long userId, Long paymentId, Integer postId, Pageable pageable);
}
