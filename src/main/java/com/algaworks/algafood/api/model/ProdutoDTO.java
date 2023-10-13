package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class ProdutoDTO {

    private Long id;

    private String nome;

    private String descricao;

    private String preco;

    private boolean ativo;

}
