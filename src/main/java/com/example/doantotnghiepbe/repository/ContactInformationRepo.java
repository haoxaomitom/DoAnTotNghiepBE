package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.ContactInformations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInformationRepo extends JpaRepository<ContactInformations,Long> {

List<ContactInformations> findContactInformationByPostPostId(Integer postId);
List<ContactInformations> findContactInformationsByPostPostIdAndWatched(Integer postId, boolean watched);
}
