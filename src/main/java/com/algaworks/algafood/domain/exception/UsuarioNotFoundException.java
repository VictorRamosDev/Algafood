package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class UsuarioNotFoundException extends EntidadeNaoEncontradaException {

    public UsuarioNotFoundException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNotFoundException(Long usuarioId) {
        super(String.format("Usuário de código %d não foi encontrado.", usuarioId));
    }
}
