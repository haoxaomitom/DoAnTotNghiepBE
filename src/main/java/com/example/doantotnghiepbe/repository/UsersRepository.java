package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
