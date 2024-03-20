package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteResumo;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class RestauranteDTO {

	@JsonView({RestauranteResumo.Resumo.class, RestauranteResumo.ApenasNome.class})
    private Long id;
	
	@JsonView({RestauranteResumo.Resumo.class, RestauranteResumo.ApenasNome.class})
    private String nome;

	@JsonView({RestauranteResumo.Resumo.class})
    private BigDecimal taxaFrete;

	@JsonView({RestauranteResumo.Resumo.class})
    private CozinhaDTO cozinha;

    private EnderecoDTO endereco;

    private boolean ativo;

    private boolean aberto;

}
