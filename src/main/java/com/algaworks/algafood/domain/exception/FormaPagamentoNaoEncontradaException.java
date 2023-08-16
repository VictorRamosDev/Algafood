package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %d", formaPagamentoId));
    }
}
