package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class CidadeDTO {

    private Long id;

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoDTO estado;

}
