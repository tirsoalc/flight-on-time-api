package com.flightontime.flightapi.domain;

public class AutorizacaoException extends RuntimeException {
    public AutorizacaoException(String mensagem) {
        super(mensagem);
    }
}