package com.flightontime.flightapi.domain.usuario;

import java.util.Set;
import java.util.stream.Collectors;

public record UsuarioDetalheDados(

        Long id,
        String nome,
        String email,
        boolean ativo,
        Set<PerfilDados> perfis

) {

    public UsuarioDetalheDados(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.isAtivo(),
                usuario.getPerfis()
                        .stream()
                        .map(p -> new PerfilDados(p.getId(), p.getNome()))
                        .collect(Collectors.toSet())
                );
    }

}
