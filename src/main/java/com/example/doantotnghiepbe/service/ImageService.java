package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.dto.ImageDTO;
import com.example.doantotnghiepbe.dto.ImagesDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImagesDTO uploadImage(MultipartFile file, Integer postId); // Upload ảnh lên Cloudinary và lưu DB
    List<ImagesDTO> getImagesByPostId(Integer postId); // Lấy ảnh theo bài đăng
//    Image saveImage(ImageDTO imageDTO);
//    List<Image> getImagesByPostId(Integer postId);
//    Image getImageById(Integer imageId);

//    byte[] getImage(Integer imageId) throws IOException;
}

