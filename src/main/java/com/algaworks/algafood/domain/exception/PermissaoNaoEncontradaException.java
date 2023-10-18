package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        super(String.format("Permissão de código %d não encontrada.", permissaoId));
    }

}
