package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.NotificationDTO;
import com.example.doantotnghiepbe.entity.Notification;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    Notification getById(Integer id) throws DataNotFoundException;
    Page<Notification> getAllByGlobalAndUser(boolean isGlobal, Long userId, int page, int size);
    Page<Notification> getAllByGlobalAndUserAndIsRead(boolean isGlobal, Long userId, boolean isRead, int page, int size);
    Page<Notification> getAllByGlobal(boolean isGlobal, int page, int size);
    Notification createNotificationGlobal(NotificationDTO notificationDTO);
    Notification createNotificationApproval(NotificationDTO notificationDTO) throws DataNotFoundException;
    void isRead(Integer notificationId) throws DataNotFoundException;
    void delete(Integer notificationId) throws DataNotFoundException;

}
