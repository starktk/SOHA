package com.auth.soha.AuthLogSoha.repository;

import com.auth.soha.AuthLogSoha.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    UserDetails findByUsername(String username);


}
