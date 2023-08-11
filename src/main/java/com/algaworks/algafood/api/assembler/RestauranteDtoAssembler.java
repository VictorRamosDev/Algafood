package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victor Ramos
 */

@Component
public class RestauranteDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).collect(Collectors.toList());
    }
}
