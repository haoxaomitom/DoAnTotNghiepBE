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
import org.springframework.stereotype.Service;

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
    public List<Notification> getAllByGlobalAndUser(boolean isGlobal, Long userId) {
        return notificationRepository.findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(true,userId);
    }

    @Override
    public List<Notification> getAllByGlobalAndUserAndIsRead(boolean isGlobal, Long userId, boolean isRead) {
        return notificationRepository.findNotificationByIsGlobalOrUserUserIdAndIsReadOrderByCreatedAtDesc(true,userId,isRead);
    }

    @Override
    public List<Notification> getAllByGlobal(boolean isGlobal) {
        return notificationRepository.findNotificationByIsGlobalOrUserUserIdOrderByCreatedAtDesc(isGlobal,null);
    }

    @Override
    public Notification createNotificationGlobal(NotificationDTO notificationDTO) {
        Notification notification = modelMapper.map(notificationDTO,Notification.class);
        notification.setIsGlobal(true);
        
        return notificationRepository.save(notification);
    }

    @Override
    public Notification createNotificationApproval(NotificationDTO notificationDTO) throws DataNotFoundException {
        Users user = usersRepository.findById(notificationDTO.getUserId()).orElseThrow(()->new DataNotFoundException("không tìm thấy người dùng"));
        Notification notification = modelMapper.map(notificationDTO,Notification.class);
        notification.setIsGlobal(false);
        notification.setUser(user);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification isRead(Integer notificationId) throws DataNotFoundException {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(()-> new DataNotFoundException("Không tìm thấy thông báo"));
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Integer notificationId) throws DataNotFoundException {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(()-> new DataNotFoundException("Không tìm thấy thông báo"));
        notificationRepository.delete(notification);
    }

}
