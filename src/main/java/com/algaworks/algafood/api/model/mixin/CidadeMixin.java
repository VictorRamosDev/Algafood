package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Victor Ramos
 */
public abstract class CidadeMixin extends SimpleModule {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;

}
