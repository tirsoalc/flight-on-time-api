package com.flightontime.flightapi.infra.client.datascience.dto;

public record DataScienceApiResposta(
   String previsao,
   Double probabilidade,
   String nivel_risco,
   String mensagem,
   DetalhesDataScience detalhes
) {}