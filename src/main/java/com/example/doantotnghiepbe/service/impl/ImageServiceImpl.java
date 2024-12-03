package com.example.doantotnghiepbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.doantotnghiepbe.dto.ImageDTO;
import com.example.doantotnghiepbe.dto.ImagesDTO;
import com.example.doantotnghiepbe.entity.Image;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.repository.ImageRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public ImagesDTO uploadImage(MultipartFile file, Integer postId) {
        try {
            // Upload file lên Cloudinary
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            // Lấy Post từ database
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            // Tạo đối tượng Image
            Image image = new Image();
            image.setImageUrl((String) uploadResult.get("url")); // Lấy URL từ Cloudinary
            image.setPost(post);

            // Lưu vào database
            image = imageRepository.save(image);

            // Chuyển sang DTO để trả về
            return new ImagesDTO(
                    image.getImageId(),
                    image.getImageUrl(),
                    postId
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }


    @Override
    public List<ImagesDTO> getImagesByPostId(Integer postId) {
        List<Image> images = imageRepository.findAll()
                .stream()
                .filter(image -> image.getPost().getPostId().equals(postId))
                .collect(Collectors.toList());

        return images.stream().map(image ->
                new ImagesDTO(
                        image.getImageId(),
                        image.getImageUrl(),
                        postId
                )).collect(Collectors.toList());
    }
}
//    private final String UPLOAD_DIR = "C:\\Users\\hp\\Pictures"; // Thư mục lưu trữ file ảnh

//    @Override
//    public Image saveImage(ImageDTO imageDTO) {
//        Post post = postRepository.findById(imageDTO.getPostID())
//                .orElseThrow(() -> new RuntimeException("Post not found with id: " + imageDTO.getPostID()));
//
//        Image image = new Image();
//        image.setImageUrl(imageDTO.getImageUrl());
//        image.setData(imageDTO.getData());
//        image.setPost(post);
//
//        return imageRepository.save(image);
//    }
//
//    @Override
//    public List<Image> getImagesByPostId(Integer postId) {
//        return imageRepository.findAll()
//                .stream()
//                .filter(image -> image.getPost().getPostId().equals(postId))
//                .toList();
//    }
//
//    @Override
//    public Image getImageById(Integer imageId) {
//        return imageRepository.findById(imageId)
//                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
//    }


