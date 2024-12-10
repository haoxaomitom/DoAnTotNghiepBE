package com.example.doantotnghiepbe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

    // Đường dẫn đến thư mục lưu ảnh
    private final String uploadDir = "C:\\Users\\hp\\Pictures"; // Đảm bảo thư mục này tồn tại (hoặc thay đổi cho phù hợp với hệ thống của bạn)

    /**
     * Phương thức lưu ảnh vào thư mục và trả về URL của ảnh
     * @param file Tệp ảnh cần tải lên
     * @return URL của ảnh đã tải lên
     * @throws IOException Nếu có lỗi trong quá trình lưu tệp
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // Kiểm tra tệp rỗng
        if (file.isEmpty()) {
            throw new IOException("Không có tệp nào được tải lên");
        }

        // Tạo tên tệp duy nhất (bằng UUID) để tránh trùng tên
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetLocation = Paths.get(uploadDir + fileName);

        // Tạo thư mục nếu nó không tồn tại
        Files.createDirectories(targetLocation.getParent());

        // Lưu tệp vào thư mục đích
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Trả về URL của ảnh đã tải lên (URL tĩnh, có thể được truy cập qua HTTP)
        return "/uploads/" + fileName;
    }
}
