package com.flightontime.flightapi.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndAtivoTrue(String email);

    boolean existsByEmail(@Email @NotBlank String email);
}
