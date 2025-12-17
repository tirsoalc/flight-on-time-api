package com.flightontime.flightapi.domain.voo.dto;

public record VooPrevisaoResposta(
    String previsao,
    Double probabilidade,
    String nivelRisco,
    String mensagem,
    DetalhesVooPrevisao detalhes
){}
