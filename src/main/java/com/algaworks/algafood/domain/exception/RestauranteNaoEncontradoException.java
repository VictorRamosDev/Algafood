package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(Long restauranteId) {
        super(String.format("Restaurante de código %d não foi encontrado.", restauranteId));
    }

}
