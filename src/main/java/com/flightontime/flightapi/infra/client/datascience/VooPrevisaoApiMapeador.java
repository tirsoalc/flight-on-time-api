package com.flightontime.flightapi.infra.client.datascience;

import com.flightontime.flightapi.domain.voo.dto.DetalhesVooPrevisao;
import com.flightontime.flightapi.domain.voo.dto.VooPrevisaoResposta;
import com.flightontime.flightapi.domain.voo.dto.VooRequisicao;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiRequisicao;
import com.flightontime.flightapi.infra.client.datascience.dto.DataScienceApiResposta;

public class VooPrevisaoApiMapeador {

    public static DataScienceApiRequisicao paraDsApiRequisicao(VooRequisicao vooRequisicao) {
        DataScienceApiRequisicao apiRequisicao = new DataScienceApiRequisicao(
                vooRequisicao.companhia(),
                vooRequisicao.origem(),
                vooRequisicao.destino(),
                vooRequisicao.dataPartida(),
                vooRequisicao.distanciaKm()
        );
        return apiRequisicao;
    }

    public static VooPrevisaoResposta paraVooPrevisaoResposta(DataScienceApiResposta apiResposta) {
        DetalhesVooPrevisao detalhesVooPrevisao = null;

        if (apiResposta.detalhes() != null) {
            detalhesVooPrevisao = new DetalhesVooPrevisao(
                    apiResposta.detalhes().is_feriado(),
                    apiResposta.detalhes().distancia_km()
            );
        }

        VooPrevisaoResposta respostaPrevisao = new VooPrevisaoResposta(
                apiResposta.previsao(),
                apiResposta.probabilidade(),
                apiResposta.nivel_risco(),
                apiResposta.mensagem(),
                detalhesVooPrevisao
        );
        return respostaPrevisao;
    }

}
