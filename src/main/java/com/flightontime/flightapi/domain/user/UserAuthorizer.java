package com.flightontime.flightapi.domain.user;

import com.flightontime.flightapi.domain.AuthorizationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class UserAuthorizer {

    public static boolean authorizeTransaction(Long id){
        var authenticatedUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for (var profile : authenticatedUser.getUser().getProfiles()){
            if (profile.getName().equals("ROLE_ADMIN") || profile.getName().equals("ROLE_INSTRUTOR")
                    || profile.getName().equals("ROLE_MODERADOR")){
                return true;
            }
        }

        if(Objects.equals(authenticatedUser.getUser().getId(), id)) {
            return true;
        }else {
            throw new AuthorizationException("Usuario autenticado não é o autor do conteúdo e não pertence ao grupo de ADMNIN/INSTRUTOR/MODERADOR");
        }
    }

}
