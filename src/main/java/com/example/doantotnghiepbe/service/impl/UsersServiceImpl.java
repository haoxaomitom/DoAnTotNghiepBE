package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.configurations.CloudinaryConfig;
import com.example.doantotnghiepbe.dto.UserInfoDTO;
import com.example.doantotnghiepbe.dto.UsersDTO;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exception.DataNotFoundException;
import com.example.doantotnghiepbe.exception.ExistingException;
import com.example.doantotnghiepbe.repository.RolesRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.UsersService;
import com.example.doantotnghiepbe.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CloudinaryConfig cloudinaryConfig;

    @Override
    public List<Users> getAll()  {
        return usersRepository.findAll();
    }

    @Override
    public Users getUsersByUsername(String username) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(username).orElseThrow(()->new DataNotFoundException("Không tìm thấy tài khoản "+ username));
        return user;
    }

    @Override
    public Users register(UsersDTO usersDTO) throws DataNotFoundException{
        if(usersRepository.existsByUsername(usersDTO.getUsername())){
            throw new ExistingException("Tên đăng nhập đã tồn tại");
        }
        if(usersRepository.existsByEmail(usersDTO.getEmail())){
            throw new ExistingException("Email đã tồn tại");
        }
        Users users = modelMapper.map(usersDTO,Users.class);
        users.setRoles(rolesRepository.findById(2).orElseThrow(()-> new DataNotFoundException("Could not find role with id: 1")));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setIsActive(true);
        return usersRepository.save(users);
    }

    @Override
    public Users updateUserInfo(UserInfoDTO userInfoDTO) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(userInfoDTO.getUsername()).orElseThrow(()->new DataNotFoundException("Không tìm thấy tài khoản "+ userInfoDTO.getUsername()));
        if(!user.getEmail().equals(userInfoDTO.getEmail())){
            if(usersRepository.existsByEmail(userInfoDTO.getEmail())){
                throw new ExistingException("Email đã tồn tại");
            }
        }
        modelMapper.map(userInfoDTO,user);
        user.setRoles(rolesRepository.findById(2).orElseThrow(()-> new DataNotFoundException("Could not find role with id: 1")));
        user.setIsActive(true);
        return usersRepository.save(user);
    }

    @Override
    public String[] login(String username, String password) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Invalid email or password"));
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        String[] result = new String[2];
        result[0] = jwtTokenUtil.generateToken(user);
        return result;
    }

    @Override
    public Users uploadAvatar(String username, MultipartFile file) throws DataNotFoundException, IOException {
        Users user = usersRepository.findUsersByUsername(username).orElseThrow(()-> new DataNotFoundException("Không tìm thấy người dùng với id: "+ username));

        user.setAvatar(cloudinaryConfig.saveToCloudinary(file));
        System.out.println("avata:" +user.getAvatar());
        return usersRepository.save(user);
    }

    @Override
    public Long countUsers() {
        return usersRepository.count();
    }
}
