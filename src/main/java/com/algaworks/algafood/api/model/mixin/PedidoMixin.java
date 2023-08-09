package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Victor Ramos
 */
public abstract class PedidoMixin extends SimpleModule {

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private FormaPagamento formaPagamento;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Restaurante restaurante;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Usuario cliente;

}
