package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.controller.Amenity;
import com.example.doantotnghiepbe.dto.*;
import com.example.doantotnghiepbe.entity.Amenities;
import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.VehicleType;
import com.example.doantotnghiepbe.repository.AmenitiesRepository;
import com.example.doantotnghiepbe.repository.ImageRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.FileUploadService;
import com.example.doantotnghiepbe.service.UpPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public UpPostServiceImpl(PostRepository postRepository, UsersRepository userRepository,
                             AmenitiesRepository amenitiesRepository, ImageRepository imageRepository,
                             FileUploadService fileUploadService, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.imageRepository = imageRepository;
        this.fileUploadService = fileUploadService;
        this.modelMapper = modelMapper;
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

    public Post createPost(PostDetailDTO postRequest) {

        Post post = new Post();
        post.setParkingName(postRequest.getParkingName());
        post.setStreet(postRequest.getStreet());
        post.setWardName(postRequest.getWardName());
        post.setDistrictName(postRequest.getDistrictName());
        post.setProvinceName(postRequest.getProvinceName());
        post.setPrice(postRequest.getPrice());
        post.setPriceUnit(postRequest.getPriceUnit());
        post.setCapacity(postRequest.getCapacity());
        post.setLatitude(postRequest.getLatitude());
        post.setLongitude(postRequest.getLongitude());
        post.setDescription(postRequest.getDescription());
        post.setStatus(postRequest.getStatus());

        // Thêm danh sách hình ảnh
        if (postRequest.getImages() != null) {
            for (ImageDTO imageUrl : postRequest.getImages()) {
                Image image = new Image();
                image.setImageUrl(String.valueOf(imageUrl));
                image.setPost(post);
                post.getImages().add(image);
            }
        }

        // Thêm danh sách loại xe
        if (postRequest.getVehicleTypes() != null) {
            for (VehicleTypeDTO vehicleTypeName : postRequest.getVehicleTypes()) {
                VehicleType vehicleType = new VehicleType();
                vehicleType.setVehicleTypeName(String.valueOf(vehicleTypeName));
                vehicleType.setPost(post);
                post.getVehicleTypes().add(vehicleType);
            }
        }

        // Thêm danh sách tiện ích
        if (postRequest.getAmenities() != null) {
            for (AmenitiesDTO amenitiesName : postRequest.getAmenities()) {
                Amenities amenities = new Amenities();
                amenities.setAmenitiesName(String.valueOf(amenitiesName));
                amenities.setPost(post);
                post.getAmenities().add(amenities);
            }
        }

        // Lưu bài đăng và các thông tin liên quan
        return postRepository.save(post);
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
