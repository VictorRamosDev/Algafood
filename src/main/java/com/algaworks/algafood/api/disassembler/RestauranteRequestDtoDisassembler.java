package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.RestauranteRequestDTO;
import com.algaworks.algafood.domain.model.Cozinha;
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

    public void copyToDomainModel(RestauranteRequestDTO restauranteRequestDTO, Restaurante restaurante) {
        // Para evitar o erro: Caused by: org.hibernate.HibernateException:
        // identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 3 to 2
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteRequestDTO, restaurante);
    }
}
