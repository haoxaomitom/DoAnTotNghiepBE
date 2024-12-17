package com.example.doantotnghiepbe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRespone {
    @JsonProperty("token")
    private String token;
    private Long userId;
    private String roleName;
}
