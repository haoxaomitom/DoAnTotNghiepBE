package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ChangePasswordDTO;
import com.example.doantotnghiepbe.dto.UserInfoDTO;
import com.example.doantotnghiepbe.dto.UserRegisterDTO;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface UsersService {
    List<Users> getAll();
    Page<Users> getAllUsers(int page, int size);
    Users getUsersByUsername(String username) throws DataNotFoundException;
    Users register (UserRegisterDTO user) throws DataNotFoundException;
    Users updateUserInfo (UserInfoDTO user) throws DataNotFoundException;
    Map login(String username, String password) throws DataNotFoundException;
    Map loginAdmin(String username, String password) throws DataNotFoundException;
    Users uploadAvatar(String username, MultipartFile file) throws DataNotFoundException, IOException;
    Users active(Long userId, boolean active) throws DataNotFoundException;
    void verified( Long userId, boolean verified) throws DataNotFoundException;
    void changePassword(Long userId, ChangePasswordDTO changePasswordDTO) throws  DataNotFoundException;
    Users getUserByTokenVerified(String tokenVerified) throws DataNotFoundException;
    Long countUsers();
    List<Object[]> getUsersByMonthAndRole(int year);
    Page<Users> searchUsers(Long userId, String username, String firstName, String lastName, String phoneNumber, int page, int size);
    void resetPassword(String token, String newPassword);
}
