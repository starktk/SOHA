package com.auth.soha.AuthLogSoha.service;

import com.auth.soha.AuthLogSoha.dto.AuthDto;
import com.auth.soha.AuthLogSoha.dto.AuthRequestDto;
import com.auth.soha.AuthLogSoha.dto.AuthResponseDto;
import com.auth.soha.AuthLogSoha.model.Usuario;
import com.auth.soha.AuthLogSoha.repository.UsuarioRepository;
import com.auth.soha.AuthLogSoha.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements UserDetailsService {



    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public ResponseEntity register(AuthRequestDto authRequestDto) {
        if (usuarioRepository.findByUsername(authRequestDto.getUsername()) != null) return ResponseEntity.badRequest().build();
        if (!verifyLenghPassword(authRequestDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        authRequestDto.setPassword(encryptedPassword(authRequestDto.getPassword()));
        Usuario userToSave = new Usuario();
        userToSave.setUsername(authRequestDto.getUsername());
        userToSave.setPassword(authRequestDto.getPassword());
        userToSave.setRole(authRequestDto.getRole());
        usuarioRepository.save(userToSave);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Boolean verifyLenghPassword(String password) {
        if (password.length() <= 0) return false;
        if (password.length() <= 4) return false;
        if(password.length() >= 15) return false;

        return true;
    }

    private String encryptedPassword(String password) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        return encryptedPassword;
    }
}
