package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactInformationRepo extends JpaRepository<ContactInformation,Long> {


}
