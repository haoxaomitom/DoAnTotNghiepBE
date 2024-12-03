package com.example.doantotnghiepbe.controller;

import com.example.doantotnghiepbe.dto.UpPostDTO;
import com.example.doantotnghiepbe.service.UpPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/upPosts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UpPostController {
    @Autowired
    private UpPostService postService;

    // Lấy tất cả các bài đăng
    @GetMapping("all")
    public ResponseEntity<List<UpPostDTO>> getAllPosts() {
        List<UpPostDTO> upPostDTOS = postService.getAllPosts();
        return ResponseEntity.ok(upPostDTOS);
    }

    // Lấy bài đăng theo ID
    @GetMapping("{id}")
    public ResponseEntity<UpPostDTO> getPostById(@PathVariable Integer id) {
        try {
            UpPostDTO upPostDTO = postService.getPostById(id);
            return new ResponseEntity<>(upPostDTO, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Thêm mới một bài đăng
    @PostMapping("savePost")
    public ResponseEntity<UpPostDTO> savePost(@RequestBody UpPostDTO upPostDTO) {
        try {
            UpPostDTO savedPost = postService.savePost(upPostDTO);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cập nhật bài đăng
    @PutMapping("{id}")
    public ResponseEntity<UpPostDTO> updatePost(@PathVariable Integer id, @RequestBody UpPostDTO upPostDTO) {
        try {
            UpPostDTO updatedUpPostDTO = postService.updatePost(id, upPostDTO);
            return new ResponseEntity<>(updatedUpPostDTO, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa bài đăng
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
