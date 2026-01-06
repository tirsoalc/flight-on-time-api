package com.flightontime.flightapi.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDataList(

        Long id,

        @JsonProperty("nome")
        String name

) {

    public UserDataList(User user) {
        this(
                user.getId(),
                user.getName()
                );
    }

}
