package com.flightontime.flightapi.controller;

import com.flightontime.flightapi.domain.voo.dto.VooPrevisaoResposta;
import com.flightontime.flightapi.domain.voo.dto.VooRequisicao;
import com.flightontime.flightapi.service.VooPrevisaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/predict")
public class VooPrevisaoController {

    @Autowired
    private VooPrevisaoService vooPrevisaoService;

    @PostMapping
    public ResponseEntity<VooPrevisaoResposta> preverAtrasoDoVoo(@RequestBody @Valid VooRequisicao vooRequisicao) {
        VooPrevisaoResposta resposta = vooPrevisaoService.preverAtraso(vooRequisicao);
        return ResponseEntity.ok(resposta);
    }

}
