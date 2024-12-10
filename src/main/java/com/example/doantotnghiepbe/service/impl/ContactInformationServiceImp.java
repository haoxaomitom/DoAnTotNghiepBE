package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.ContactInformationDTO;
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
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdOrderByCreateAtDesc(postId);
        return contactInformations;
    }

    @Override
    public ContactInformations getById(Long id) throws DataNotFoundException {
        ContactInformations contactInformations = contactInformationRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Không tìm thấy thông tin"));
        return contactInformations;
    }

    @Override
    public ContactInformations create(ContactInformationDTO contactInformationDTO, int postId) throws DataNotFoundException {
        Post post = postRepository.findByPostId(postId);
        Users user = usersRepository.findById(post.getUser().getUserId()).orElseThrow(()->new DataNotFoundException("Không tìm thấy người dùng với id "+post.getUser().getUserId()));
        ContactInformations contactInformation = modelMapper.map(contactInformationDTO,ContactInformations.class);
        contactInformation.setUser(user);
        return contactInformationRepo.save(contactInformation);
    }

    @Override
    public void delete(Long id) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()-> new DataNotFoundException("Không tim thấy thông tin với id là "+id));
        contactInformationRepo.delete(contactInformation);
    }

    @Override
    public List<ContactInformations> findByWatched( Integer postId, boolean watched) {
        List<ContactInformations> contactInformations = contactInformationRepo.findContactInformationsByUserUserIdAndWatchedOrderByCreateAtDesc(postId,watched);
        return contactInformations;
    }

    @Override
    public ContactInformations watched(Long id) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông tin với id" + id));
        contactInformation.setWatched(true);

        return contactInformationRepo.save(contactInformation);
    }

    @Override
    public ContactInformations contacted(Long id, boolean contacted) throws DataNotFoundException {
        ContactInformations contactInformation = contactInformationRepo.findById(id).orElseThrow(()->new DataNotFoundException("Không tìm thấy thông tin với id" + id));
        contactInformation.setContacted(contacted);

        return contactInformationRepo.save(contactInformation);
    }
}
