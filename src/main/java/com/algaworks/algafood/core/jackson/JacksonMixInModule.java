package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.model.RestauranteMixIn;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixInModule extends SimpleModule {

    public JacksonMixInModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixIn.class);
    }

}
