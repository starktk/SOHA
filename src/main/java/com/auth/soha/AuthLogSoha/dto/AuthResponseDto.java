package com.auth.soha.AuthLogSoha.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

    private String token;

    public AuthResponseDto(String token) {
        this.token = token;
    }
}
