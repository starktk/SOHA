package com.auth.soha.AuthLogSoha;

import com.auth.soha.AuthLogSoha.dto.AuthRequestDto;
import com.auth.soha.AuthLogSoha.model.Usuario;
import com.auth.soha.AuthLogSoha.repository.UsuarioRepository;
import com.auth.soha.AuthLogSoha.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("Bruna");
        authRequestDto.setPassword("123767");

        when(usuarioRepository.findByUsername(authRequestDto.getUsername())).thenReturn(null);

        ResponseEntity responseEntity = authService.register(authRequestDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testRegisterUsernameExists() {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("raul");
        authRequestDto.setPassword("12");

        Mockito.when(usuarioRepository.findByUsername(authRequestDto.getUsername())).thenReturn(new Usuario());

        ResponseEntity responseEntity = authService.register(authRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testRegisterInvalidPassword() {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("testuser");
        authRequestDto.setPassword("123");

        ResponseEntity responseEntity = authService.register(authRequestDto);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }
}
