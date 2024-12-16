package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Users;
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
    Optional<Users> findUsersByUsername(String username);
    Optional<Users> findUsersByTokenVerified(String tokenVerified);

    Users findByUserId(Long user);
    List<Users> findUsersByRolesRoleName(String roleName);
    @Query("SELECT FUNCTION('MONTH', u.createdAt) AS month, COUNT(u) AS count " +
            "FROM Users u " +
            "WHERE u.roles.roleId = 2 AND FUNCTION('YEAR', u.createdAt) = :year " +
            "GROUP BY FUNCTION('MONTH', u.createdAt) " +
            "ORDER BY month")
    List<Object[]> countUsersByMonthAndRole(@Param("year") int year);
    long countUsersByRolesRoleId(long roleId);
}

