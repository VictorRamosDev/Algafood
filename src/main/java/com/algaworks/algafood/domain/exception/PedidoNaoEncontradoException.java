package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Pedido de código %s não encontrado.", codigoPedido));
    }

}
