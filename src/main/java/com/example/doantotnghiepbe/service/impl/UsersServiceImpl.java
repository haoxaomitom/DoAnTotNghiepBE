package com.example.doantotnghiepbe.service.impl;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
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


//    private static final String FACEBOOK_API_URL = "https://graph.facebook.com/v12.0/me?fields=id,email,first_name,last_name&access_token=";



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
    public Map login(String username, String password) throws DataNotFoundException {
        Users user = usersRepository.findUsersByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Invalid email or password"));
        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        Map result =new HashMap();
        result.put("token",jwtTokenUtil.generateToken(user));
        result.put("userId", user.getUserId());
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

//    @Override
//    public Map<String, Object> loginWithFacebook(String facebookToken) throws DataNotFoundException {
//        // Call Facebook API to get user information
//        String facebookUrl = FACEBOOK_API_URL + facebookToken;
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> fbUser = restTemplate.getForObject(facebookUrl, Map.class);
//
//        if (fbUser == null || !fbUser.containsKey("id")) {
//            throw new DataNotFoundException("Facebook user not found");
//        }
//
//        String facebookId = (String) fbUser.get("id");
//        Users user = usersRepository.findByFacebookId(facebookId).orElseThrow(() -> new DataNotFoundException("Facebook account not linked"));
//
//        // Generate JWT token
//        String token = jwtTokenUtil.generateToken(user);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("token", token);
//        result.put("userId", user.getUserId());
//        return result;
//    }
//
//    @Override
//    public Map<String, Object> registerWithFacebook(String facebookToken) throws DataNotFoundException {
//        return Map.of();
//    }
//
//    @Override
//    public Map<String, Object> loginOrRegisterGoogleUser(String googleIdToken) throws DataNotFoundException {
//        return Map.of();
//    }

//    @Override
//    public Map<String, Object> registerWithFacebook(String facebookToken) throws DataNotFoundException {
//        // Call Facebook API to get user information
//        String facebookUrl = FACEBOOK_API_URL + facebookToken;
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> fbUser = restTemplate.getForObject(facebookUrl, Map.class);
//
//        if (fbUser == null || !fbUser.containsKey("id")) {
//            throw new DataNotFoundException("Facebook user not found");
//        }
//
//        String facebookId = (String) fbUser.get("id");
//        String email = (String) fbUser.get("email");
//        String firstName = (String) fbUser.get("first_name");
//        String lastName = (String) fbUser.get("last_name");
//
//        // Check if user exists by Facebook ID or email
//        Users user = usersRepository.findByFacebookId(facebookId).orElse(null);
//
//        if (user == null) {
//            user = usersRepository.findByEmail(email).orElse(null);
//        }
//
//        if (user == null) {
//            // Create new user
//            user = new Users();
//            user.setFacebookId(facebookId);
//            user.setEmail(email);
//            user.setFirstName(firstName);
//            user.setLastName(lastName);
//            user.setUsername(email != null ? email : "fb_" + facebookId);
//            user.setIsActive(true);
//            user.setPassword("");  // No password needed for Facebook login
//            user.setRoles(rolesRepository.findById(2).orElseThrow(() -> new DataNotFoundException("Role not found")));
//            usersRepository.save(user);
//        } else {
//            // Update user info if necessary
//            user.setFacebookId(facebookId);
//            usersRepository.save(user);
//        }
//
//        // Generate JWT token
//        String token = jwtTokenUtil.generateToken(user);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("token", token);
//        result.put("userId", user.getUserId());
//        return result;
//    }
//
//    public Users getOrCreateUserWithGoogle(String googleId) throws DataNotFoundException {
//        // Call Google API to fetch user info if needed
//        Users user = usersRepository.findByGoogleId(googleId)
//                .orElseThrow(() -> new DataNotFoundException("Google account not found"));
//
//        if (user == null) {
//            // If the user does not exist, create a new user
//            user = new Users();
//            user.setGoogleId(googleId);
//            user.setIsActive(true);
//            user.setRoles(rolesRepository.findById(2).orElseThrow(() -> new DataNotFoundException("Role not found")));
//            usersRepository.save(user);
//        }
//
//        return user;
//    }


}
