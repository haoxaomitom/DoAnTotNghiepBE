package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.configurations.CloudinaryConfig;
import com.example.doantotnghiepbe.dto.ChangePasswordDTO;
import com.example.doantotnghiepbe.dto.UserInfoDTO;
import com.example.doantotnghiepbe.dto.UserRegisterDTO;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.exceptions.ExistingException;
import com.example.doantotnghiepbe.repository.RolesRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.UsersService;
import com.example.doantotnghiepbe.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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


    public Page<Users> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usersRepository.findUsersByRolesRoleName("USER", pageable);
    }

    @Override
    public Users getUsersByUsername(String username) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(username).orElseThrow(()->new DataNotFoundException("Không tìm thấy tài khoản "+ username));
        return user;
    }

    @Override
    public Users register(UserRegisterDTO userRegisterDTO) throws DataNotFoundException{
        if(usersRepository.existsByUsername(userRegisterDTO.getUsername())){
            throw new ExistingException("Tên đăng nhập đã tồn tại.");
        }
        if(usersRepository.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())){
            throw new ExistingException("Số điện thoại đã tồn tại.");
        }
        Users users = modelMapper.map(userRegisterDTO,Users.class);
        users.setRoles(rolesRepository.findById(2).orElseThrow(()-> new DataNotFoundException("Could not find role with id: 2")));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setIsActive(true);
        users.setVerified(false);
        users.setAvatar("https://via.placeholder.com/100");
        return usersRepository.save(users);
    }

    @Override
    public Users registerStaff(UserRegisterDTO userRegisterDTO) throws DataNotFoundException {
        if(usersRepository.existsByUsername(userRegisterDTO.getUsername())){
            throw new ExistingException("Tên đăng nhập đã tồn tại.");
        }
        if(usersRepository.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())){
            throw new ExistingException("Số điện thoại đã tồn tại.");
        }
        Users users = modelMapper.map(userRegisterDTO,Users.class);
        users.setRoles(rolesRepository.findById(3).orElseThrow(()-> new DataNotFoundException("Could not find role with id: 2")));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setIsActive(true);
        users.setVerified(false);
        users.setAvatar("https://via.placeholder.com/100");
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
    public Map login(String username, String password) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Tên đăng nhập hoặc mật khẩu không đúng."));
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng.");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        Map result =new HashMap();
        result.put("token",jwtTokenUtil.generateToken(user));
        result.put("userId", user.getUserId());
        return result;
    }

    @Override
    public Map loginAdmin(String username, String password) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Tên đăng nhập hoặc mật khẩu không đúng."));
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng.");
        }
        if (!user.getRoles().getRoleName().equals("ADMIN") && !user.getRoles().getRoleName().equals("STAFF")){
            throw new BadCredentialsException("Bạn không có quyền truy cập.");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        Map result =new HashMap();
        result.put("token",jwtTokenUtil.generateToken(user));
        result.put("userId", user.getUserId());
        result.put("roleName", user.getRoles().getRoleName());
        System.out.println(user.getRoles().getRoleName());
        return result;
    }

    @Override
    public Users uploadAvatar(String username, MultipartFile file) throws DataNotFoundException, IOException {
        Users user = usersRepository.findUsersByUsername(username).orElseThrow(()-> new DataNotFoundException("Không tìm thấy người dùng với tên đăng nhập: "+ username));
        user.setAvatar(cloudinaryConfig.saveToCloudinary(file));
        System.out.println("avata:" +user.getAvatar());
        return usersRepository.save(user);
    }

    @Override
    public Users active(Long userId, boolean active) throws DataNotFoundException {
        Users user = usersRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("Không tìm thấy người dùng với id: "+ userId));
        user.setIsActive(active);
        return usersRepository.save(user);
    }

    @Override
    public void verified(Long userId, boolean verified) throws DataNotFoundException {
        Users user = usersRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("không tìm thấy người dùng với id: "+userId));
        user.setVerified(verified);
        usersRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO changePasswordDTO) throws DataNotFoundException {
        Users user = usersRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("không tìm thấy người dùng với id: "+userId));
        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.getPassword())){
            throw new BadCredentialsException("Mật khẩu cũ không đúng");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirm())){
            throw new IllegalArgumentException("Xác nhận mật khẩu sai!");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        usersRepository.save(user);
    }

    @Override
    public Users getUserByTokenVerified(String tokenVerified) throws DataNotFoundException {
        Users user = usersRepository.findUsersByTokenVerified(tokenVerified).orElseThrow(()-> new DataNotFoundException("Lỗi xác nhận!"));
        if (jwtTokenUtil.isTokenExpired(tokenVerified)) {
            throw new RuntimeException("Đã quá thời gian xác nhận");
        }
        user.setVerified(true);
        usersRepository.save(user);
        return user;
    }

    @Override
    public Long countUsers() {
        return usersRepository.count();
    }

    @Override
    public List<Object[]> getUsersByMonthAndRole(int year) {
        return usersRepository.countUsersByMonthAndRole(year);
    }

    @Override
    public Page<Users> searchUsers(Long userId, String username, String firstName, String lastName, String phoneNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usersRepository.searchUsers(userId, username, firstName, lastName, phoneNumber, pageable);

    }

    @Override
    public void resetPassword(String token, String newPassword) {
        Users user = usersRepository.findUsersByTokenForgotPassword(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTokenForgotPassword(null); // Xóa token sau khi đặt lại
        usersRepository.save(user);
    }

}
