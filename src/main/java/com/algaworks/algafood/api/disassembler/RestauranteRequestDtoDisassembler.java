package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.RestauranteRequestDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
public class RestauranteRequestDtoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainModel(RestauranteRequestDTO request) {
        return modelMapper.map(request, Restaurante.class);
    }
}
