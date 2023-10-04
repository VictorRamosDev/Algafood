package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.UsuarioRequestDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioRequestDtoDisassembler {

    private final ModelMapper modelMapper;

    public Usuario toDomainModel(UsuarioRequestDTO request) {
        return modelMapper.map(request, Usuario.class);
    }

    public void copyToDomainModel(UsuarioRequestDTO request, Usuario usuarioAtual) {
        modelMapper.map(request, usuarioAtual);
    }
}
