package com.flightontime.flightapi.domain.usuario;

import com.flightontime.flightapi.domain.AutorizacaoException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class UsuarioAutorize {

    public static boolean autorizeTransacao(Long id){
        var usuarioAutenticado = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (var perfil : usuarioAutenticado.getUsuario().getPerfis()){
            if (perfil.getNome().equals("ROLE_ADMIN") || perfil.getNome().equals("ROLE_INSTRUTOR")
                    || perfil.getNome().equals("ROLE_MODERADOR")){
                return true;
            }
        }

        if(Objects.equals(usuarioAutenticado.getUsuario().getId(), id)) {
            return true;
        }else {
            throw new AutorizacaoException("Usuario autenticado não é o autor do conteúdo e não pertence ao grupo de ADMNIN/INSTRUTOR/MODERADOR");
        }
    }

}
