package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.RestauranteRequestDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
public class RestauranteRequestDtoDisassembler {

    public Restaurante toDomainModel(RestauranteRequestDTO request) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(request.getNome());
        restaurante.setTaxaFrete(request.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(request.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}
