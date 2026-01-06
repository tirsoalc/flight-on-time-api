package com.flightontime.flightapi.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthData(
        String email,
        @JsonProperty("senha")
        String password
) {
}
