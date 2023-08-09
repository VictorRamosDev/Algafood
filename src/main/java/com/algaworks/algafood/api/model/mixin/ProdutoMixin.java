package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Victor Ramos
 */
public abstract class ProdutoMixin extends SimpleModule {

    @JsonIgnore
    private Restaurante restaurante;

}
