package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.NotificationDTO;
import com.example.doantotnghiepbe.entity.Notification;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.repository.NotificationRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Notification getById(Integer id) throws DataNotFoundException {
        return notificationRepository.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông báo"));
    }

    @Override
    public Page<Notification> getAllByGlobalAndUser(boolean isGlobal, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(true,userId,pageable);
    }

    @Override
    public Page<Notification> getAllByGlobalAndUserAndIsRead(boolean isGlobal, Long userId, boolean isRead, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findNotificationsByGlobalAndUseridAndIsRead(true,userId,isRead,pageable);
    }

    @Override
    public Page<Notification> getAllByGlobal(boolean isGlobal, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository.findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(isGlobal,null,pageable);
    }

    @Override
    public Notification createNotificationGlobal(NotificationDTO notificationDTO) {
        Notification notification = modelMapper.map(notificationDTO,Notification.class);
        notification.setIsGlobal(true);
        notification.setUser(null);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification createNotificationApproval(NotificationDTO notificationDTO) throws DataNotFoundException {
        Users user = usersRepository.findById(notificationDTO.getUserId()).orElseThrow(()->new DataNotFoundException("không tìm thấy người dùng"));
        Notification notification = modelMapper.map(notificationDTO,Notification.class);
        notification.setNotificationId(null);
        notification.setIsGlobal(false);
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public void isRead(Integer notificationId) throws DataNotFoundException {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(()-> new DataNotFoundException("Không tìm thấy thông báo"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void delete(Integer notificationId) throws DataNotFoundException {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(()-> new DataNotFoundException("Không tìm thấy thông báo"));
        notificationRepository.delete(notification);
    }

}
