package com.flightontime.flightapi.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDetailsResponse(

        Long id,

        @JsonProperty("nome")
        String name,

        String email,

        @JsonProperty("ativo")
        boolean active,

        @JsonProperty("perfis")
        Set<ProfileData> profiles

) {

    public UserDetailsResponse(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getProfiles()
                        .stream()
                        .map(p -> new ProfileData(p.getId(), p.getName()))
                        .collect(Collectors.toSet())
                );
    }

}
