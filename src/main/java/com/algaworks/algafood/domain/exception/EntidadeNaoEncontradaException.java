package com.algaworks.algafood.domain.exception;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException {

//    public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
//        super(status, mensagem);
//    }

//    public EntidadeNaoEncontradaException(String mensagem) {
//        this(HttpStatus.NOT_FOUND, mensagem);
//    }

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

}
