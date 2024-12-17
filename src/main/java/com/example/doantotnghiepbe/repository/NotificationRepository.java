package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    Page<Notification> findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(boolean isGlobal, Long userId, Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE (n.isGlobal = :isGlobal OR n.user.userId = :userId) AND n.isRead = :isRead ORDER BY n.createdAt DESC")
    Page<Notification> findNotificationsByGlobalAndUseridAndIsRead(@Param("isGlobal") boolean isGlobal, @Param("userId") Long userId, @Param("isRead") boolean isRead, Pageable pageable);
    Page<Notification> findByIsGlobalOrUserUserIdAndIsReadOrderByCreatedAtDesc(boolean isGlobal, Long userId, boolean isRead, Pageable pageable);

}
