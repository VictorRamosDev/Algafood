package com.algaworks.algafood.domain.exception;

/**
 * @author Victor Ramos
 */
public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        super(String
                .format(
                        "Não existe um cadastro de produto com código %d para o restaurante de código %d",
                        produtoId,
                        restauranteId
                )
        );
    }

}
