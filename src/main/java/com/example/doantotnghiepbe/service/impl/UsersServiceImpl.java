package com.example.doantotnghiepbe.service.impl;

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

import java.util.List;

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

    @Override
    public List<Users> getAll()  {
        return usersRepository.findAll();
    }

    @Override
    public Users getUsersByUsername(String username) throws DataNotFoundException {
        Users user = usersRepository.findById(username).orElseThrow(()->new DataNotFoundException("Không tìm thấy tài khoản "+ username));
        return user;
    }

    @Override
    public Users register(UsersDTO usersDTO) throws DataNotFoundException{
        if(usersRepository.existsById(usersDTO.getUsername())){
            throw new ExistingException("Tên đăng nhập đã tồn tại");
        }
        if(usersRepository.existsByEmail(usersDTO.getEmail())){
            throw new ExistingException("Email đã tồn tại");
        }
        Users users = modelMapper.map(usersDTO,Users.class);
        users.setIdRole(rolesRepository.findById(2).orElseThrow(()-> new DataNotFoundException("Could not find role with id: 1")));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setIsActive(true);
        return usersRepository.save(users);
    }

    @Override
    public Users updateUserInfo(UserInfoDTO userDTO) throws DataNotFoundException {
        Users user = usersRepository.findById(userDTO.getUsername()).orElseThrow(()->new DataNotFoundException("Không tìm thấy tài khoản "+ userDTO.getUsername()));
        if(!user.getEmail().equals(userDTO.getEmail())){
            if(usersRepository.existsByEmail(userDTO.getEmail())){
                throw new ExistingException("Email đã tồn tại");
            }
        }
        user = modelMapper.map(userDTO,Users.class);
        user.setIdRole(rolesRepository.findById(2).orElseThrow(()-> new DataNotFoundException("Could not find role with id: 1")));
        user.setIsActive(true);
        return usersRepository.save(user);
    }

    @Override
    public String[] login(String username, String password) throws DataNotFoundException {
        Users user = usersRepository.findById(username)
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
}
