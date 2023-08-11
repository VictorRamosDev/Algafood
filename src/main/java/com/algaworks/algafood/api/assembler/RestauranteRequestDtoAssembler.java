package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CozinhaIdRequestDTO;
import com.algaworks.algafood.api.model.RestauranteRequestDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
public class RestauranteRequestDtoAssembler {

    public RestauranteRequestDTO toRequestModel(Restaurante restaurante) {
        RestauranteRequestDTO restauranteRequestDTO = new RestauranteRequestDTO();
        restauranteRequestDTO.setNome(restaurante.getNome());
        restauranteRequestDTO.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIdRequestDTO cozinhaIdRequestDTO = new CozinhaIdRequestDTO();
        cozinhaIdRequestDTO.setId(restaurante.getCozinha().getId());
        restauranteRequestDTO.setCozinha(cozinhaIdRequestDTO);

        return restauranteRequestDTO;
    }
}
