package com.flightontime.flightapi.service;

import com.flightontime.flightapi.domain.voo.dto.VooPrevisaoResposta;
import com.flightontime.flightapi.domain.voo.dto.VooRequisicao;
import com.flightontime.flightapi.infra.client.datascience.DataScienceClienteInterface;
import com.flightontime.flightapi.infra.client.datascience.VooPrevisaoApiMapeador;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiRequisicao;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VooPrevisaoService {

    @Autowired
    private DataScienceClienteInterface cliente;

    public VooPrevisaoResposta preverAtraso(VooRequisicao vooRequisicao) {
        DataScienceApiRequisicao dsApiRequisicao = VooPrevisaoApiMapeador.paraDsApiRequisicao(vooRequisicao);
        DataScienceApiResposta respostaApi = cliente.obterPrevisaoDoVoo(dsApiRequisicao);
        return VooPrevisaoApiMapeador.paraVooPrevisaoResposta(respostaApi);
    }

}
