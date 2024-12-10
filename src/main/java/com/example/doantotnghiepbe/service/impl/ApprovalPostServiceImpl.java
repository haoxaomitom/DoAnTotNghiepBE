package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.ApprovalPost;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.repository.ApprovalPostRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UserRepository;
import com.example.doantotnghiepbe.service.ApprovalPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ApprovalPostServiceImpl implements ApprovalPostService {

    private static final Logger logger = Logger.getLogger(ApprovalPostServiceImpl.class.getName());

    @Autowired
    private ApprovalPostRepository approvalPostRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Lấy tất cả các ApprovalPost.
     *
     * @return danh sách ApprovalPost
     */
    @Override
    public List<ApprovalPost> getAllApprovalPosts() {
        logger.info("Fetching all approval posts from the database.");
        return approvalPostRepository.findAll();
    }

    /**
     * Lấy thông tin ApprovalPost theo ID.
     *
     * @param id ID của ApprovalPost
     * @return Optional chứa ApprovalPost nếu tìm thấy
     */
    @Override
    public Optional<ApprovalPost> getApprovalPostById(Integer id) {
        logger.info("Fetching approval post with ID: " + id);
        return approvalPostRepository.findById(id);
    }

    @Override
    public ApprovalPost approvePost(Integer id, String reviewedByUsername) {
        // Tìm bài viết cần duyệt
        ApprovalPost approvalPost = approvalPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApprovalPost with ID " + id + " not found"));

        // Lấy User từ tên người duyệt bài
        Users reviewedByUser = userRepository.findByUsername(reviewedByUsername)
                .orElseThrow(() -> new RuntimeException("User with username " + reviewedByUsername + " not found"));

        // Cập nhật trạng thái ApprovalPost
        approvalPost.setStatus("Approved");
        approvalPost.setReviewedByUser(reviewedByUser); // Cập nhật người duyệt bài
        approvalPost.setReviewedAt(LocalDateTime.now()); // Thời gian duyệt bài
        approvalPost.setRejectionReason(null); // Xóa lý do từ chối nếu bài được duyệt
        approvalPostRepository.save(approvalPost); // Lưu ApprovalPost đã cập nhật

        // Cập nhật trạng thái Post liên quan
        Post post = approvalPost.getPost();
        if (post == null) {
            throw new RuntimeException("Related post for ApprovalPost ID " + id + " not found");
        }
        post.setStatus("Approved"); // Cập nhật trạng thái Post thành Approved
        postRepository.save(post); // Lưu Post đã cập nhật

        return approvalPost; // Trả về ApprovalPost đã được duyệt
    }




    /**
     * Từ chối bài đăng.
     *
     * @param id              ID của ApprovalPost
     * @param rejectionReason Lý do từ chối
     * @return ApprovalPost đã bị từ chối
     */
    @Override
    public ApprovalPost rejectPost(Integer id, String rejectionReason, String reviewedByUsername) {
        logger.info("Rejecting post with approvalPost ID: " + id);

        // Tìm kiếm ApprovalPost theo ID
        ApprovalPost approvalPost = approvalPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApprovalPost with ID " + id + " not found"));

        // Lấy User từ tên người dùng đã duyệt bài
        Users reviewedByUser = userRepository.findByUsername(reviewedByUsername)
                .orElseThrow(() -> new RuntimeException("User with username " + reviewedByUsername + " not found"));

        // Cập nhật trạng thái ApprovalPost
        approvalPost.setStatus("Rejected");
        approvalPost.setRejectionReason(rejectionReason);  // Cập nhật lý do từ chối
        approvalPost.setReviewedAt(LocalDateTime.now());   // Cập nhật thời gian duyệt
        approvalPost.setReviewedByUser(reviewedByUser);    // Cập nhật người duyệt bài
        approvalPostRepository.save(approvalPost);         // Lưu ApprovalPost đã cập nhật

        // Cập nhật trạng thái Post liên quan
        Post post = approvalPost.getPost();
        if (post == null) {
            throw new RuntimeException("Related post for ApprovalPost ID " + id + " not found");
        }
        post.setStatus("Rejected");  // Cập nhật trạng thái Post thành Rejected
        postRepository.save(post);   // Lưu Post đã cập nhật

        logger.info("Post rejected successfully: ApprovalPost ID = " + id);
        return approvalPost;
    }
}
