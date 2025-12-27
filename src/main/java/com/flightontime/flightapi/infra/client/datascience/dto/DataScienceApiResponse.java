package com.flightontime.flightapi.infra.client.datascience.dto;

public record DataScienceApiResponse(
   String previsao,
   Double probabilidade,
   String nivel_risco,
   String mensagem,
   DataScienceResponseDetails detalhes
) {}