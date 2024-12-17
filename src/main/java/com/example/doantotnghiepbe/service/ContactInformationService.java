package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.dto.ContactInformationResDTO;
import com.example.doantotnghiepbe.entity.ContactInformations;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactInformationService {
    Page<ContactInformationResDTO> findAllByUserId(Integer userId, int page, int size);
    Page<ContactInformationResDTO> findAllByPostId(Integer postId, int page, int size);
    ContactInformationResDTO getById(Long id) throws DataNotFoundException;
    ContactInformationResDTO create(ContactInformationDTO contactInformationDTO, int postId) throws DataNotFoundException;
    void delete(Long id) throws DataNotFoundException;
    Page<ContactInformationResDTO> findByPostIdAndWatched(Integer postId, boolean watched, int page, int size);
    Page<ContactInformationResDTO> findByUserIdAndWatched(Integer userId, boolean watched, int page, int size);
    Page<ContactInformationResDTO> findByPostIdAndContacted(Integer postId, boolean contacted, int page, int size);
    Page<ContactInformationResDTO> findByUserIdAndContacted(Integer userId, boolean contacted, int page, int size);
    List<Integer> getPostId(Integer userId);
    void watched(Long id)throws DataNotFoundException;
    void contacted(Long id, boolean contacted) throws DataNotFoundException;
}
