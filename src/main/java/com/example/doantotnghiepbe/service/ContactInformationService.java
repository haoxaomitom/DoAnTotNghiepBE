package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.dto.ContactInformationResDTO;
import com.example.doantotnghiepbe.entity.ContactInformations;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactInformationService {
    List<ContactInformationResDTO> findAllByUserId(Integer userId);
    List<ContactInformationResDTO> findAllByPostId(Integer postId);
    ContactInformationResDTO getById(Long id) throws DataNotFoundException;
    ContactInformationResDTO create(ContactInformationDTO contactInformationDTO, int postId) throws DataNotFoundException;
    void delete(Long id) throws DataNotFoundException;
    List<ContactInformationResDTO> findByPostIdAndWatched(Integer postId, boolean watched);
    List<ContactInformationResDTO> findByUserIdAndWatched(Integer userId, boolean watched);
    List<ContactInformationResDTO> findByPostIdAndContacted(Integer postId, boolean contacted);
    List<ContactInformationResDTO> findByUserIdAndContacted(Integer userId, boolean contacted);
    List<Integer> getPostId(Integer userId);
    void watched(Long id)throws DataNotFoundException;
    void contacted(Long id, boolean contacted) throws DataNotFoundException;
}
