package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.CozinhaRequestDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CozinhaRequestDtoDisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toDomainModel(CozinhaRequestDTO cozinhaRequestDTO) {
        return modelMapper.map(cozinhaRequestDTO, Cozinha.class);
    }

    public void copyToDomainModel(CozinhaRequestDTO request, Cozinha cozinha) {
        modelMapper.map(request, cozinha);
    }
}
