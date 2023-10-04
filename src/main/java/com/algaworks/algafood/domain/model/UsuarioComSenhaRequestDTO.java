package com.algaworks.algafood.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class UsuarioComSenhaRequestDTO extends UsuarioRequestDTO {

    @NotBlank
    private String senha;
}
