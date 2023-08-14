package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.EstadoRequestDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstadoRequestDtoDisassembler {

    private final ModelMapper modelMapper;

    public Estado toDomainModel(EstadoRequestDTO request) {
        return modelMapper.map(request, Estado.class);
    }

    public void copyToDomainModel(EstadoRequestDTO request, Estado estado) {
        modelMapper.map(request, estado);
    }
}
