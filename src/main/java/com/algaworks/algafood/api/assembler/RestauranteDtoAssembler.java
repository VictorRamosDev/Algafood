package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victor Ramos
 */

@Component
public class RestauranteDtoAssembler {

    public RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setId(restaurante.getId());
        dto.setNome(restaurante.getNome());
        dto.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());
        dto.setCozinha(cozinhaDTO);

        return dto;
    }

    public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).collect(Collectors.toList());
    }
}
