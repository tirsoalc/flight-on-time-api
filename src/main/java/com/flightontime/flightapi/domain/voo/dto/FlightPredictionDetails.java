package com.flightontime.flightapi.domain.voo.dto;

public record FlightPredictionDetails(
        Double distancia,
        Double chuva,
        Double vento,
        String fonte_clima
) {
}
