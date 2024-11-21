package com.example.doantotnghiepbe.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer commentId;
    private Integer post;
    private Long user;
    private List<CommentUserDTO> cud;
    private String commentContent;
    private LocalDateTime createdAt;
}
