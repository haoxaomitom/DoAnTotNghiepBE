package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.dto.ContactInformationResDTO;
import com.example.doantotnghiepbe.entity.ContactInformations;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.repository.ContactInformationRepo;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.ContactInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactInformationServiceImp implements ContactInformationService {
    @Autowired
    private ContactInformationRepo contactInformationRepo;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ContactInformationResDTO> findAllByUserId(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdOrderByCreateAtDesc(userId,pageable);
        Page<ContactInformationResDTO> responses = contactInformations.map(contact -> modelMapper.map(contact, ContactInformationResDTO.class));
        return responses;
    }

    @Override
    public Page<ContactInformationResDTO> findAllByPostId(Integer postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdOrderByCreateAtDesc(postId,pageable);
        Page<ContactInformationResDTO> responses = contactInformations.map(contact -> modelMapper.map(contact, ContactInformationResDTO.class));
        return responses;
    }

    @Override
    public ContactInformationResDTO getById(Long id) throws DataNotFoundException {
        ContactInformations contactInformations = contactInformationRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Không tìm thấy thông tin"));
        ContactInformationResDTO responses = modelMapper.map(contactInformations,ContactInformationResDTO.class);
        return responses;
    }

    @Override
    public ContactInformationResDTO create(ContactInformationDTO contactInformationDTO, int postId) throws DataNotFoundException {
        Post post = postRepository.findByPostId(postId);
        Users user = usersRepository.findById(post.getUser().getUserId()).orElseThrow(()->new DataNotFoundException("Không tìm thấy người dùng với id "+post.getUser().getUserId()));
        ContactInformations contactInformation = modelMapper.map(contactInformationDTO,ContactInformations.class);
        contactInformation.setUser(user);
        contactInformation.setPost(post);
        ContactInformationResDTO responses = modelMapper.map(contactInformationRepo.save(contactInformation),ContactInformationResDTO.class);
        return responses;
    }

    @Override
    public void delete(Long id) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Không tim thấy thông tin với id là "+id));
        contactInformationRepo.delete(contactInformation);
    }

    @Override
    public Page<ContactInformationResDTO> findByPostIdAndWatched( Integer postId, boolean watched,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdAndWatchedOrderByCreateAtDesc(postId,watched,pageable);
        Page<ContactInformationResDTO> responses = contactInformations.map(contact -> modelMapper.map(contact, ContactInformationResDTO.class));
        return responses;
    }

    @Override
    public Page<ContactInformationResDTO> findByUserIdAndWatched(Integer userId, boolean watched, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdAndWatchedOrderByCreateAtDesc(userId,watched,pageable);
        Page<ContactInformationResDTO> responses = contactInformations.map(contact -> modelMapper.map(contact, ContactInformationResDTO.class));
        return responses;
    }

    @Override
    public Page<ContactInformationResDTO> findByPostIdAndContacted(Integer postId, boolean contacted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdAndContactedOrderByCreateAtDesc(postId,contacted,pageable);
        Page<ContactInformationResDTO> responses = contactInformations.map(contact -> modelMapper.map(contact, ContactInformationResDTO.class));
        return responses;
    }

    @Override
    public Page<ContactInformationResDTO> findByUserIdAndContacted(Integer userId, boolean contacted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdAndContactedOrderByCreateAtDesc(userId,contacted,pageable);
        Page<ContactInformationResDTO> responses = contactInformations.map(contact -> modelMapper.map(contact, ContactInformationResDTO.class));
        return responses;
    }

    @Override
    public List<Integer> getPostId(Integer userId) {
        return null;
    }

    @Override
    public void watched(Long id) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông tin với id" + id));
        contactInformation.setWatched(true);
        contactInformationRepo.save(contactInformation);
    }

    @Override
    public void contacted(Long id, boolean contacted) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông tin với id" + id));
        contactInformation.setContacted(contacted);
        contactInformationRepo.save(contactInformation);
    }
}
