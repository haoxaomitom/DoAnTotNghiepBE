package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.ContactInformations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInformationRepo extends JpaRepository<ContactInformations,Long> {

Page<ContactInformations> findContactInformationsByUserUserIdOrderByCreateAtDesc(Integer userId , Pageable pageable);
    Page<ContactInformations> findContactInformationsByUserUserIdAndWatchedOrderByCreateAtDesc(Integer userId, boolean watched , Pageable pageable);
    Page<ContactInformations> findContactInformationsByUserUserIdAndContactedOrderByCreateAtDesc(Integer userId, boolean contacted , Pageable pageable);
    Page<ContactInformations> findContactInformationsByPostPostIdOrderByCreateAtDesc(Integer postId , Pageable pageable);
    Page<ContactInformations> findContactInformationsByPostPostIdAndWatchedOrderByCreateAtDesc(Integer postId, boolean watched, Pageable pageable);
    Page<ContactInformations> findContactInformationsByPostPostIdAndContactedOrderByCreateAtDesc(Integer postId, boolean contacted, Pageable pageable);

}
