package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class CidadeRequestDTO {

    @NotNull
    private String nome;

    @Valid
    @NotNull
    private EstadoIdRequestDTO estado;

}
