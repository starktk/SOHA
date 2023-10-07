package com.auth.soha.AuthLogSoha.dto;

import com.auth.soha.AuthLogSoha.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    private String username;
    private String password;
    private UserRole role;
}
