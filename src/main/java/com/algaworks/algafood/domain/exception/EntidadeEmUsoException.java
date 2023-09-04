package com.algaworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends NegocioException {

    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }

    public EntidadeEmUsoException(String message, Long id) {
        super(String.format(message, id));
    }
}
