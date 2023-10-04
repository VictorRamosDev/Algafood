package com.algaworks.algafood.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private List<GrupoDTO> grupos;

}
