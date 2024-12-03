package com.example.doantotnghiepbe.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    /**
     * Upload một file lên Cloudinary.
     *
     * @param file File cần upload.
     * @return URL của ảnh đã upload.
     * @throws IOException Nếu có lỗi khi upload.
     */
    String uploadImage(MultipartFile file) throws IOException;

    /**
     * Xóa một file khỏi Cloudinary.
     *
     * @param publicId ID công khai của file trên Cloudinary.
     * @return Trạng thái xóa thành công.
     * @throws IOException Nếu có lỗi khi xóa.
     */
    String deleteImage(String publicId) throws IOException;
}