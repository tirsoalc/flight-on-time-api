package com.flightontime.flightapi.infra.client.datascience;

import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiRequisicao;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiResposta;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface DataScienceClienteInterface {

    @PostExchange("/predict")
    DataScienceApiResposta obterPrevisaoDoVoo(@RequestBody DataScienceApiRequisicao requisicao);

}
