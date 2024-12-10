package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.entity.ContactInformations;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactInformationService {
    List<ContactInformations> findAllByPostId(Integer postId);
    ContactInformations create(ContactInformationDTO contactInformationDTO) throws DataNotFoundException;
    ContactInformations update(Long id, ContactInformationDTO contactInformationDTO) throws DataNotFoundException;
    void delete(Long id) throws DataNotFoundException;
    List<ContactInformations> findByWatched(Integer postId, boolean watched);
    ContactInformations watched(Long id)throws DataNotFoundException;
}
