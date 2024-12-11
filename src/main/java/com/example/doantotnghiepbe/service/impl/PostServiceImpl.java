package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.dto.PostDTO;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final PaymentRepository postPaymentRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, PaymentRepository postPaymentRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.postPaymentRepository = postPaymentRepository;
    }

//    @Override
//    public Page<PostDTO> findAllByOrderByCreatedAtDesc(Pageable pageable) {
//        Page<Post> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
//        return postPage.map(this::convertToDTO);
//    }

    @Override
    public Page<PostDTO> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        // Lấy danh sách Post từ repository
        Page<Post> postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        // Dùng ModelMapper để chuyển đổi từng Post sang PostDTO
        List<PostDTO> postDTOs = postPage.getContent().stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class); // Chuyển Post -> PostDTO
                    postDTO.setCommentCount(post.getCommentCount()); // Thêm thuộc tính không tự động ánh xạ
                    return postDTO;
                })
                .collect(Collectors.toList());

        // Trả về Page<PostDTO>
        return new PageImpl<>(postDTOs, pageable, postPage.getTotalElements());
    }

    @Override
    public List<Object[]> countPostsByDistrict() {
        return postRepository.countPostsByDistrict();
    }

    @Override
    public Page<PostDTO> searchPosts(String searchTerm, Pageable pageable) {
        Page<Post> postPage = postRepository.searchPosts(searchTerm, pageable);
        return postPage.map(this::convertToDTO);
    }

    @Override
    public Page<PostDTO> searchPostsByVehicleType(String vehicleType, Pageable pageable) {
        Page<Post> postPage = postRepository.searchPostsByVehicleType(vehicleType, pageable);
        return postPage.map(this::convertToDTO);
    }

    @Override
    public Page<PostDTO> sortPostsByPrice(String sort, Pageable pageable) {
        Page<Post> postPage;
        if ("desc".equalsIgnoreCase(sort)) {
            postPage = postRepository.findAllByOrderByPriceDesc(pageable);
        } else {
            postPage = postRepository.findAllByOrderByPriceAsc(pageable);
        }
        return postPage.map(this::convertToDTO);
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setCommentCount(post.getCommentCount());
        return postDTO;
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        // Get all posts by userId from the repository
        List<Post> posts = postRepository.findAllByUserUserId(userId);

        // Convert the list of Post to PostDTO
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public Page<PostDTO> findAllTopPostsOrderByPaymentAndDate(Pageable pageable) {
        Page<Post> topPosts = postRepository.findAllTopPostsOrderByPaymentAndDate(pageable);
        return topPosts.map(this::convertToDTO);
    }

    public void softDeletePost(Integer postId) {
        postRepository.softDeletePostById(postId);
    }

    public Page<Post> getPostsByUserIdAndStatus(Long userId, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllByUserUserIdAndStatus(userId, status, pageable);
    }

    public Page<PostDTO> findByPostId(String postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findByPostIdContaining(postId, pageable);

        // Chuyển đổi Page<Post> sang Page<PostDTO>
        List<PostDTO> postDTOs = postPage.getContent().stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class); // Sử dụng ModelMapper
                    postDTO.setCommentCount(post.getCommentCount()); // Thêm các thuộc tính tùy chỉnh
                    return postDTO;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(postDTOs, pageable, postPage.getTotalElements());
    }


}

