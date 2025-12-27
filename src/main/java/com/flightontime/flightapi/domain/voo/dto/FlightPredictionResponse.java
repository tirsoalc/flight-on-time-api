package com.flightontime.flightapi.domain.voo.dto;

public record FlightPredictionResponse(
    String previsao,
    Double probabilidade,
    String cor,
    FlightPredictionDetails detalhes
){}
