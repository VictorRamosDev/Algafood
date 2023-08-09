package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Victor Ramos
 */
public abstract class ItemPedidoMixin extends SimpleModule {

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Pedido pedido;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Produto produto;

}
