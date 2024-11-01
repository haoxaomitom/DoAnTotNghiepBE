package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByVnpTxnRef(String vnpTxnRef);
}
