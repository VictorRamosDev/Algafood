package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class CozinhaRequestDTO {

    @NotBlank
    private String nome;

}
