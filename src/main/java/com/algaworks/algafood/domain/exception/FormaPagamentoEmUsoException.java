package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class FormaPagamentoEmUsoException extends EntidadeEmUsoException {

    public FormaPagamentoEmUsoException(String message) {
        super(message);
    }

    public FormaPagamentoEmUsoException(Long formaPagamentoId) {
        this(String.format("A forma de pagamento com código %d está em uso", formaPagamentoId));
    }
}
