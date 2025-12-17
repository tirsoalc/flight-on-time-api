package com.flightontime.flightapi.domain.voo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VooRequisicao(
        @NotBlank String companhia,
        @NotBlank String origem,
        @NotBlank String destino,
        @JsonProperty("data_partida") @NotNull LocalDateTime dataPartida,
        @JsonProperty("distancia_km") @NotNull Double distanciaKm
) {
}
