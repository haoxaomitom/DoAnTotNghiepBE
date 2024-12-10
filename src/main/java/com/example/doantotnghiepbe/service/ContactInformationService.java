package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.entity.ContactInformations;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactInformationService {
    List<ContactInformations> findAllByPostId(Integer postId);
    ContactInformations getById(Long id) throws DataNotFoundException;
    ContactInformations create(ContactInformationDTO contactInformationDTO, int postId) throws DataNotFoundException;
    void delete(Long id) throws DataNotFoundException;
    List<ContactInformations> findByWatched(Integer postId, boolean watched);
    ContactInformations watched(Long id)throws DataNotFoundException;
    ContactInformations contacted(Long id, boolean contacted) throws DataNotFoundException;
}
