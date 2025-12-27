package com.flightontime.flightapi.infra.client.datascience.dto;

public record DataScienceResponseDetails(
        Boolean is_feriado,
        Double distancia_km
) {}