package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String Username);
    Optional<Users> findUsersByUsername(String username);
//    Optional<Users> findByFacebookId(String facebookId); // Thêm mới
    Users findByEmail(String email);
}
