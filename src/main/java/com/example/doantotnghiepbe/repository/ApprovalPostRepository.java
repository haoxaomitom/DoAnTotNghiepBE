package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovalPostRepository extends JpaRepository<ApprovalPost, Integer> {

}
