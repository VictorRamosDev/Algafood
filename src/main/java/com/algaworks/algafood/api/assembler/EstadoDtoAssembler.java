package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstadoDtoAssembler {

    private final ModelMapper modelMapper;

    public EstadoDTO toModel(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
        return estados.stream().map(this::toModel).collect(Collectors.toList());
    }
}
