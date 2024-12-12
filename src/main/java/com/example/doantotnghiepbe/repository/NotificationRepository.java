package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(boolean isGlobal, Long userId);
    List<Notification> findNotificationByIsGlobalOrUserUserIdAndIsReadOrderByCreatedAtDesc(boolean isGlobal, Long userId, boolean isRead);

}
