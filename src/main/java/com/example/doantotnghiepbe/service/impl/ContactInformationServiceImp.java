package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
import com.example.doantotnghiepbe.entity.ContactInformations;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exception.DataNotFoundException;
import com.example.doantotnghiepbe.repository.ContactInformationRepo;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.ContactInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<ContactInformations> findAllByPostId(Integer postId) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationByPostPostId(postId);
        return contactInformations;
    }

    @Override
    public ContactInformations create(ContactInformationDTO contactInformationDTO) throws DataNotFoundException {
        Users user = usersRepository.findById(contactInformationDTO.getUser()).orElseThrow(()->new DataNotFoundException("Không tìm thấy người dùng với id "+contactInformationDTO.getUser()));
        Post post = postRepository.findByPostId(contactInformationDTO.getPost());
        ContactInformations contactInformation = modelMapper.map(contactInformationDTO,ContactInformations.class);
        contactInformation.setUser(user);
        contactInformation.setPost(post);
        contactInformation.setWatched(false);
        return contactInformationRepo.save(contactInformation);
    }

    @Override
    public ContactInformations update(Long id, ContactInformationDTO contactInformationDTO) throws DataNotFoundException {
        Users user = usersRepository.findById(contactInformationDTO.getUser()).orElseThrow(()->new DataNotFoundException("Không tìm thấy người dùng với id "+contactInformationDTO.getUser()));
        Post post = postRepository.findByPostId(contactInformationDTO.getPost());
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông tin với id" + id));
        modelMapper.map(contactInformationDTO,contactInformation);
        contactInformation.setUser(user);
        contactInformation.setPost(post);
        return contactInformationRepo.save(contactInformation);
    }

    @Override
    public void delete(Long id) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Không tim thấy thông tin với id là "+id));
        contactInformationRepo.delete(contactInformation);
    }

    @Override
    public List<ContactInformations> findByWatched( Integer postId, boolean watched) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdAndWatched(postId,watched);
        return contactInformations;
    }

    @Override
    public ContactInformations watched(Long id) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông tin với id" + id));
        contactInformation.setWatched(true);

        return contactInformationRepo.save(contactInformation);
    }
}
