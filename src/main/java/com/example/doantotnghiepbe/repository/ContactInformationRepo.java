package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.ContactInformations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInformationRepo extends JpaRepository<ContactInformations,Long> {

List<ContactInformations> findContactInformationsByUserUserIdOrderByCreateAtDesc(Integer userId);
List<ContactInformations> findContactInformationsByUserUserIdAndWatchedOrderByCreateAtDesc(Integer userId, boolean watched);
List<ContactInformations> findContactInformationsByUserUserIdAndContactedOrderByCreateAtDesc(Integer userId, boolean contacted);
List<ContactInformations> findContactInformationsByPostPostIdOrderByCreateAtDesc(Integer postId);
List<ContactInformations> findContactInformationsByPostPostIdAndWatchedOrderByCreateAtDesc(Integer postId, boolean watched);
List<ContactInformations> findContactInformationsByPostPostIdAndContactedOrderByCreateAtDesc(Integer postId, boolean contacted);

}
