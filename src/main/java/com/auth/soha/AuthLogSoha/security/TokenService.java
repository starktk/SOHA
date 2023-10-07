package com.auth.soha.AuthLogSoha.security;

import com.auth.soha.AuthLogSoha.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;


    public String genetareToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-SOHA-api")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(generateTimeForValidation())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while genetare token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm  = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-SOHA-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch(JWTVerificationException exception) {
            return "";

        }
    }

    private Instant generateTimeForValidation() {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
    }
}
