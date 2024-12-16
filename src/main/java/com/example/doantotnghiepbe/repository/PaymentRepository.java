package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.dto.PaymentUserDTO;
import com.example.doantotnghiepbe.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByVnpTxnRef(String vnpTxnRef);

    @Query("SELECT p FROM Payment p WHERE p.postId.user.id = :userId")
    List<Payment> findPaymentsByUserId(@Param("userId") Long userId);

    @Query("SELECT FUNCTION('MONTH', p.paymentDate) AS month, SUM(p.paymentAmount) AS totalRevenue " +
            "FROM Payment p " +
            "WHERE FUNCTION('YEAR', p.paymentDate) = :year AND p.paymentStatus = 'Success' " +
            "GROUP BY FUNCTION('MONTH', p.paymentDate) " +
            "ORDER BY month")

    List<Object[]> getRevenueByMonth(@Param("year") int year);
    long countPaymentByPaymentStatus(String status);
    @Query("SELECT SUM(p.paymentAmount) FROM Payment p WHERE p.paymentStatus = 'Successful'")
    long getTotalRevenue();
}
