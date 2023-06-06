package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        super(String.format("Cozinha de código %d não foi encontrada.", cozinhaId));
    }

}
