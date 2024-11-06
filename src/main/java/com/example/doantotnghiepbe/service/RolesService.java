package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.Roles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolesService {
    List<Roles> getAll();

}
