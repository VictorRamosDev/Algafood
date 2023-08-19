package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class EnderecoTinyDTO {

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeTinyDTO cidade;

}
