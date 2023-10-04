package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.api.model.PermissaoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class GrupoDTO {

    private Long id;

    private String nome;

    private List<PermissaoDTO> permissoes;

}
