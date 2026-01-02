package com.flightontime.flightapi.infra.exception;

import com.flightontime.flightapi.domain.AutorizacaoException;
import com.flightontime.flightapi.domain.DataScienceApiOfflineException;
import com.flightontime.flightapi.domain.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream()
                .map(ErroValidacaoDados::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ExceptionMensagemDados(ex.getMessage()));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(new ExceptionMensagemDados(ex.getMessage()));
    }

    @ExceptionHandler(AutorizacaoException.class)
    public ResponseEntity<?> tratarAutorizacaoException(AutorizacaoException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionMensagemDados(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionMensagemDados("Credenciais inválidas"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionMensagemDados("Falha na autenticação"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionMensagemDados("Acesso negado"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionMensagemDados(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(DataScienceApiOfflineException.class)
    public ResponseEntity<ExceptionMensagemDados> handleDataScienceApiOffline(DataScienceApiOfflineException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ExceptionMensagemDados(ex.getMessage()));
    }

    private record ErroValidacaoDados(String campo, String mensagem) {
        public ErroValidacaoDados(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    private record ExceptionMensagemDados(
            String type,
            String msg
    ){
        public ExceptionMensagemDados(String m){
            this("Exception", m);
        }
    }
}
