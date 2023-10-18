package com.algaworks.algafood.api.model;

import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
public class RestauranteRequestDTO {

    @NotBlank
    private String nome;

    @Multiplo(numero = 5)
    @TaxaFrete
    @NotNull
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdRequestDTO cozinha;

    @Valid
    @NotNull
    private EnderecoRequestDTO endereco;

    @NotNull
    private boolean ativo;

    @NotNull
    private boolean aberto;

}
