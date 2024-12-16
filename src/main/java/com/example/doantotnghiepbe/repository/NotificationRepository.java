package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(boolean isGlobal, Long userId);
    @Query("SELECT n FROM Notification n WHERE (n.isGlobal = :isGlobal OR n.user.userId = :userId) AND n.isRead = :isRead ORDER BY n.createdAt DESC")
    List<Notification> findNotificationsByGlobalAndUseridAndIsRead(@Param("isGlobal") boolean isGlobal, @Param("userId") Long userId, @Param("isRead") boolean isRead);
    List<Notification> findByIsGlobalOrUserUserIdAndIsReadOrderByCreatedAtDesc(boolean isGlobal, Long userId, boolean isRead);

}
