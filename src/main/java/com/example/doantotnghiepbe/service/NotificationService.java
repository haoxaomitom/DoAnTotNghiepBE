package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.NotificationDTO;
import com.example.doantotnghiepbe.entity.Notification;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    Notification getById(Integer id) throws DataNotFoundException;
    List<Notification> getAllByGlobalAndUser(boolean isGlobal, Long userId);
    List<Notification> getAllByGlobalAndUserAndIsRead(boolean isGlobal, Long userId, boolean isRead);
    List<Notification> getAllByGlobal(boolean isGlobal);
    Notification createNotificationGlobal(NotificationDTO notificationDTO);
    Notification createNotificationApproval(NotificationDTO notificationDTO) throws DataNotFoundException;
    Notification isRead(Integer notificationId) throws DataNotFoundException;
    void delete(Integer notificationId) throws DataNotFoundException;

}
