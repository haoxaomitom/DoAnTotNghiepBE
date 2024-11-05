package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.UserInfoDTO;
import com.example.doantotnghiepbe.dto.UsersDTO;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UsersService {
    List<Users> getAll();
    Users getUsersByUsername(String username) throws DataNotFoundException;
    Users register (UsersDTO user) throws DataNotFoundException;
    Users updateUserInfo (UserInfoDTO user) throws DataNotFoundException;
    String[]  login(String username, String password) throws DataNotFoundException;
    Users uploadAvatar(String username, MultipartFile file) throws DataNotFoundException, IOException;
    Long countUsers();
}
