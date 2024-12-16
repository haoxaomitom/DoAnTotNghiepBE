package com.example.doantotnghiepbe.repository;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import com.example.doantotnghiepbe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApprovalPostRepository extends JpaRepository<ApprovalPost, Integer> {

    ApprovalPost findByPostPostId(Integer postId);

    @Query("SELECT ap FROM ApprovalPost ap WHERE CAST(ap.approvalPostId AS string) LIKE %:approvalPostId%")
    Page<ApprovalPost> searchByApprovalPostIdLike(@Param("approvalPostId") String approvalPostId, Pageable pageable);

    @Query("""
        SELECT ap
        FROM ApprovalPost ap
        ORDER BY 
            CASE WHEN ap.status = 'WAITING' THEN 0 ELSE 1 END ASC,
            ap.reviewedAt DESC
    """)
    Page<ApprovalPost> findAllWithWaitingFirst(Pageable pageable);

    Optional<ApprovalPost> findByPost(Post post);
}
