package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.api.model.view.RestauranteResumo;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class CozinhaDTO {

    @NotNull
    @JsonView({RestauranteResumo.Resumo.class})
    private Long id;

    @NotBlank
    @JsonView({RestauranteResumo.Resumo.class})
    private String nome;

}
