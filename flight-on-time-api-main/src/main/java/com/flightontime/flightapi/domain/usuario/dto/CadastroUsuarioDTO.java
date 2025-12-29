package com.flightontime.flightapi.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroUsuarioDTO(
        @NotBlank
        String nome,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String senha
) {
}
