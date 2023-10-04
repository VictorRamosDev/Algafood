package com.algaworks.algafood.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class SenhaRequestDTO {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String senhaNova;
}
