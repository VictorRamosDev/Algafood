package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.RestauranteRequestDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
public class RestauranteRequestDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteRequestDTO toRequestModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteRequestDTO.class);
    }
}
