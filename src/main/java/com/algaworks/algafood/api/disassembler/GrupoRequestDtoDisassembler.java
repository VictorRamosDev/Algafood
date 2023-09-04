package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.GrupoRequestDTO;
import com.algaworks.algafood.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GrupoRequestDtoDisassembler {

    private final ModelMapper modelMapper;

    public Grupo toDomainModel(GrupoRequestDTO request) {
        return modelMapper.map(request, Grupo.class);
    }

    public void copyToDomainModel(GrupoRequestDTO request, Grupo grupo) {
        modelMapper.map(request, grupo);
    }

}
