package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.model.mixin.*;
import com.algaworks.algafood.domain.model.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixInModule extends SimpleModule {

    public JacksonMixInModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Estado.class, EstadoMixin.class);
        setMixInAnnotation(ItemPedido.class, ItemPedidoMixin.class);
        setMixInAnnotation(Pedido.class, PedidoMixin.class);
        setMixInAnnotation(Produto.class, ProdutoMixin.class);
    }

}
