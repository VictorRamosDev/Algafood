package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format("Forma de pagamento de código %d não foi encontrada.", formaPagamentoId));
    }
}
