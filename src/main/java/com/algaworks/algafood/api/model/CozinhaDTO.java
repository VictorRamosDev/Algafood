package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class CozinhaDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;

}
