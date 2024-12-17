package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String Username);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Users> findUsersByTokenForgotPassword(String tokenForgotPassword);
    Optional<Users> findUsersByUsername(String username);
    Optional<Users> findUsersByTokenVerified(String tokenVerified);
    Page<Users> findUsersByRolesRoleName(String roleName, Pageable pageable);
    @Query("SELECT FUNCTION('MONTH', u.createdAt) AS month, COUNT(u) AS count " +
            "FROM Users u " +
            "WHERE u.roles.roleId = 2 AND FUNCTION('YEAR', u.createdAt) = :year " +
            "GROUP BY FUNCTION('MONTH', u.createdAt) " +
            "ORDER BY month")
    List<Object[]> countUsersByMonthAndRole(@Param("year") int year);
    long countUsersByRolesRoleId(long roleId);
    @Query("SELECT u FROM Users u " +
            "WHERE (:userId IS NULL OR u.userId = :userId) " +
            "AND (:username IS NULL OR u.username LIKE %:username%) " +
            "AND (:firstName IS NULL OR u.firstName LIKE %:firstName%) " +
            "AND (:lastName IS NULL OR u.lastName LIKE %:lastName%) " +
            "AND (:phoneNumber IS NULL OR u.phoneNumber LIKE %:phoneNumber%)")
    Page<Users> searchUsers(
            @Param("userId") Long userId,
            @Param("username") String username,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phoneNumber") String phoneNumber,
            Pageable pageable
    );

    Users findByUserId(Long userId);
}

