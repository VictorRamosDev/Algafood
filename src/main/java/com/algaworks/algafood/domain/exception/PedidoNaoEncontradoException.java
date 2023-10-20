package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        super(String.format("Pedido de código %d não encontrado.", pedidoId));
    }
}
