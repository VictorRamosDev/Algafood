package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        super(String.format("Grupo de código %d não foi encontrado.", grupoId));
    }
}
