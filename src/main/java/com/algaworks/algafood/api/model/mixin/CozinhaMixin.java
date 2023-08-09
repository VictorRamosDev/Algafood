package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

/**
 * @author Victor Ramos
 */
public abstract class CozinhaMixin extends SimpleModule {

    @JsonIgnore
    private List<Restaurante> restaurantes;

}
