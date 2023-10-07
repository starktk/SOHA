package com.auth.soha.AuthLogSoha.controller;

import com.auth.soha.AuthLogSoha.dto.AuthDto;
import com.auth.soha.AuthLogSoha.dto.AuthRequestDto;
import com.auth.soha.AuthLogSoha.dto.AuthResponseDto;
import com.auth.soha.AuthLogSoha.model.Usuario;
import com.auth.soha.AuthLogSoha.security.TokenService;
import com.auth.soha.AuthLogSoha.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDto auth) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
        var manager = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.genetareToken((Usuario) manager.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("registrar")
    public ResponseEntity register(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(authService.register(authRequestDto));
    }
}
