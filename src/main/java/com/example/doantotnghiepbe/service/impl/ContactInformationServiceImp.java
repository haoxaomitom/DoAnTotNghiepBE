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
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ContactInformationResDTO> findAllByUserId(Integer userId) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdOrderByCreateAtDesc(userId);
        List<ContactInformationResDTO> responses = contactInformations.stream()
                .map(contact -> modelMapper.map(contact, ContactInformationResDTO.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<ContactInformationResDTO> findAllByPostId(Integer postId) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdOrderByCreateAtDesc(postId);
        List<ContactInformationResDTO> responses = contactInformations.stream()
                .map(contact -> modelMapper.map(contact, ContactInformationResDTO.class))
                .collect(Collectors.toList());
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
    public List<ContactInformationResDTO> findByPostIdAndWatched( Integer postId, boolean watched) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdAndWatchedOrderByCreateAtDesc(postId,watched);
        List<ContactInformationResDTO> responses = contactInformations.stream()
                .map(contact -> modelMapper.map(contact, ContactInformationResDTO.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<ContactInformationResDTO> findByUserIdAndWatched(Integer userId, boolean watched) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdAndWatchedOrderByCreateAtDesc(userId,watched);
        List<ContactInformationResDTO> responses = contactInformations.stream()
                .map(contact -> modelMapper.map(contact, ContactInformationResDTO.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<ContactInformationResDTO> findByPostIdAndContacted(Integer postId, boolean contacted) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByPostPostIdAndContactedOrderByCreateAtDesc(postId,contacted);

        List<ContactInformationResDTO> responses = contactInformations.stream()
                .map(contact -> modelMapper.map(contact, ContactInformationResDTO.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<ContactInformationResDTO> findByUserIdAndContacted(Integer userId, boolean contacted) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdAndContactedOrderByCreateAtDesc(userId,contacted);

        List<ContactInformationResDTO> responses = contactInformations.stream()
                .map(contact -> modelMapper.map(contact, ContactInformationResDTO.class))
                .collect(Collectors.toList());
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
