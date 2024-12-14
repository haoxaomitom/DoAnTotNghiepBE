package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.dto.*;
import com.example.doantotnghiepbe.entity.*;
import com.example.doantotnghiepbe.repository.*;
import com.example.doantotnghiepbe.service.FileUploadService;
import com.example.doantotnghiepbe.service.UpPostService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpPostServiceImpl implements UpPostService {

    private final PostRepository postRepository;
    private final UsersRepository userRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final ImageRepository imageRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final ApprovalPostRepository approvalPostRepository;

    @Autowired
    public UpPostServiceImpl(PostRepository postRepository, UsersRepository userRepository,
                             AmenitiesRepository amenitiesRepository, ImageRepository imageRepository,
                             FileUploadService fileUploadService, ModelMapper modelMapper, Cloudinary cloudinary, VehicleTypeRepository vehicleTypeRepository, ApprovalPostRepository approvalPostRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.imageRepository = imageRepository;
        this.fileUploadService = fileUploadService;
        this.modelMapper = modelMapper;
        this.cloudinary = cloudinary;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.approvalPostRepository = approvalPostRepository;
    }


    @Override
    public List<UpPostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UpPostDTO getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return convertToDTO(post);
    }

    @Override
    public UpPostDTO savePost(UpPostDTO upPostDTO) {
        Post post = convertToEntity(upPostDTO);
        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    @Override
    public UpPostDTO updatePost(Integer id, UpPostDTO upPostDTO) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        Post updatedPost = convertToEntity(upPostDTO);
        updatedPost.setPostId(id);
        updatedPost.setCreatedAt(existingPost.getCreatedAt()); // Preserve original createdAt timestamp
        Post savedPost = postRepository.save(updatedPost);
        return convertToDTO(savedPost);
    }

    @Override
    public void deletePost(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }
    @Transactional
    public Post createPost(PostDetailDTO postRequest) {
        // Step 1: Map PostDetailDTO to Post entity
        Post post = modelMapper.map(postRequest, Post.class);

        // Set User
        post.setUser(userRepository.findById(postRequest.getUserId().longValue())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + postRequest.getUserId())));

        if (post.getStatus() == null) {
            post.setStatus("WAITING"); // Default status value
        }

        if (post.getCommentCount() == null) {
            post.setCommentCount(0); // Default commentCount value
        }

        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now()); // Default createdAt value
        }

        // Step 2: Save post to get its ID
        Post savedPost = postRepository.save(post);

        // Step 3: Save VehicleTypes separately
        if (postRequest.getVehicleTypes() != null) {
            postRequest.getVehicleTypes().forEach(vehicleTypeDTO -> {
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleTypesName(vehicleTypeDTO.getVehicleTypesName());
                vehicleType.setPost(savedPost); // Associate with saved post
                vehicleTypeRepository.save(vehicleType); // Save to repository
            });
        }

        // Step 4: Save Amenities separately
        if (postRequest.getAmenities() != null) {
            postRequest.getAmenities().forEach(amenitiesDTO -> {
                Amenities amenities = new Amenities();
                amenities.setAmenitiesName(amenitiesDTO.getAmenitiesName());
                amenities.setPost(savedPost); // Associate with saved post
                amenitiesRepository.save(amenities); // Save to repository
            });
        }
        ApprovalPost approvalPost = new ApprovalPost();
        approvalPost.setPost(savedPost); // Associate with saved post
        approvalPost.setStatus("WAITING"); // Default status
        approvalPostRepository.save(approvalPost); // Save to repository

        // Return saved post
        return savedPost;
    }
        
    @Transactional
    public List<Image> uploadImages(Integer postId, List<MultipartFile> imageFiles) {
        // Find the post
        Post post = postRepository.findByPostId(postId);


        List<Image> uploadedImages = new ArrayList<>();

        // Upload images and link to the post
        if (imageFiles != null && !imageFiles.isEmpty()) {
            imageFiles.forEach(file -> {
                try {
                    // Upload image to Cloudinary
                    var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("url");

                    // Create Image entity and associate with post
                    Image image = new Image();
                    image.setImageUrl(imageUrl);
                    image.setPost(post);
                    post.getImages().add(image);
                    uploadedImages.add(image);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload image", e);
                }
            });
        }

        // Save the post with updated images
        postRepository.save(post);

        return uploadedImages;
    }


    private UpPostDTO convertToDTO(Post post) {
        UpPostDTO dto = new UpPostDTO();
        dto.setPostId(post.getPostId());
        dto.setUserId(Math.toIntExact(post.getUser().getUserId()));
        dto.setParkingName(post.getParkingName());
        dto.setStreet(post.getStreet());
        dto.setWardName(post.getWardName());
        dto.setDistrictName(post.getDistrictName());
        dto.setProvinceName(post.getProvinceName());
        dto.setPrice(post.getPrice());
        dto.setPriceUnit(post.getPriceUnit());
        dto.setCapacity(post.getCapacity());
        dto.setLatitude(post.getLatitude());
        dto.setLongitude(post.getLongitude());
        dto.setDescription(post.getDescription());
        dto.setStatus(post.getStatus());
        dto.setCommentCount(post.getCommentCount());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setImages(post.getImages().stream().map(image -> image.getImageUrl()).collect(Collectors.toList()));
        dto.setAmenities(post.getAmenities().stream().map(amenity -> amenity.getAmenitiesName()).collect(Collectors.toList()));
        return dto;
    }

    private Post convertToEntity(UpPostDTO dto) {
        Post post = new Post();
        post.setPostId(dto.getPostId());
        post.setUser(userRepository.findById(dto.getUserId().longValue())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId())));
        post.setParkingName(dto.getParkingName());
        post.setStreet(dto.getStreet());
        post.setWardName(dto.getWardName());
        post.setDistrictName(dto.getDistrictName());
        post.setProvinceName(dto.getProvinceName());
        post.setPrice(dto.getPrice());
        post.setPriceUnit(dto.getPriceUnit());
        post.setCapacity(dto.getCapacity());
        post.setLatitude(dto.getLatitude());
        post.setLongitude(dto.getLongitude());
        post.setDescription(dto.getDescription());
        post.setStatus(dto.getStatus());
        post.setCommentCount(dto.getCommentCount());
        post.setCreatedAt(dto.getCreatedAt());
        return post;
    }


}
//    @Override
//    @Transactional
//    public void savePost(PostDTO postDTO, List<String> amenities, List<MultipartFile> images) {
//        // 1. Tạo đối tượng Post từ PostDTO
//        Post post = new Post();
//        User user = userRepository.findById(postDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
//
//        post.setUser(user);
//        post.setParkingName(postDTO.getParkingName());
//        post.setStreet(postDTO.getStreet());
//        post.setWardName(postDTO.getWardName());
//        post.setDistrictName(postDTO.getDistrictName());
//        post.setProvinceName(postDTO.getProvinceName());
//        post.setPrice(postDTO.getPrice());
//        post.setPriceUnit(postDTO.getPriceUnit());
//        post.setCapacity(postDTO.getCapacity());
//        post.setLatitude(postDTO.getLatitude());
//        post.setLongitude(postDTO.getLongitude());
//        post.setDescription(postDTO.getDescription());
//        post.setStatus(postDTO.getStatus());
//        post.setCommentCount(postDTO.getCommentCount());
//        post.setCreatedAt(LocalDateTime.now());
//
//        // Lưu bài đăng vào cơ sở dữ liệu
//        postRepository.save(post);
//
//        // 2. Lưu các tiện ích
//        for (String amenityName : amenities) {
//            Amenities amenity = new Amenities();
//            amenity.setAmenitiesName(amenityName);
//            amenity.setPost(post);
//            amenitiesRepository.save(amenity);
//        }
//
//        // 3. Lưu các ảnh
//        for (MultipartFile image : images) {
//            try {
//                Image img = new Image();
//                img.setImageUrl(saveImage(image));  // Giả sử bạn lưu ảnh và trả về URL
//                img.setPost(post);
//                imageRepository.save(img);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @Override
//    public void savePost(Post post) {
//        // Lưu bài đăng (Post)
//        postRepository.save(post);
//
//        // Lưu ảnh liên quan đến bài đăng (Image)
//        List<Image> images = post.getImages();
//        if (images != null && !images.isEmpty()) {
//            for (Image image : images) {
//                image.setPost(post); // Thiết lập mối quan hệ với Post
//                imageRepository.save(image); // Lưu ảnh vào database
//            }
//        }
//
//        // Lưu tiện ích liên quan đến bài đăng (Amenities)
//        List<Amenities> amenities = post.getAmenities();
//        if (amenities != null && !amenities.isEmpty()) {
//            for (Amenities amenity : amenities) {
//                amenity.setPost(post); // Thiết lập mối quan hệ với Post
//                amenitiesRepository.save(amenity); // Lưu tiện ích vào database
//            }
//        }
//    }

// Giả sử bạn lưu ảnh và trả về URL của ảnh
//    private String saveImage(MultipartFile image) throws IOException {
//        // Xử lý lưu trữ ảnh (ví dụ: lưu vào thư mục hoặc dịch vụ lưu trữ)
//        return "url_to_saved_image";  // Trả về URL hoặc đường dẫn lưu ảnh
//    }

// Save new post
//    @Override
//    public PostDTO savePost(PostDTO postDTO) {
//        User user = userRepository.findById(postDTO.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));
//
//        Post post = convertToEntity(postDTO);
//        post.setUser(user);
//        post.setCreatedAt(LocalDateTime.now());
//
//        Post savedPost = postRepository.save(post);
//
//    private Post convertToEntity(PostDTO postDTO) {
//        Post post = new Post();
//        post.setParkingName(postDTO.getParkingName());
//        post.setStreet(postDTO.getStreet());
//        post.setWardName(postDTO.getWardName());
//        post.setDistrictName(postDTO.getDistrictName());
//        post.setProvinceName(postDTO.getProvinceName());
//        post.setPrice(postDTO.getPrice());
//        post.setPriceUnit(postDTO.getPriceUnit());
//        post.setCapacity(postDTO.getCapacity());
//        post.setLatitude(postDTO.getLatitude());
//        post.setLongitude(postDTO.getLongitude());
//        post.setDescription(postDTO.getDescription());
//        post.setStatus(postDTO.getStatus());
//        return post;
//    }


//}
