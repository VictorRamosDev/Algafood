package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.ProdutoRequestDTO;
import com.algaworks.algafood.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoRequestDtoDisassembler {

    private final ModelMapper modelMapper;

    public Produto toDomainModel(ProdutoRequestDTO request) {
        return modelMapper.map(request, Produto.class);
    }

    public void copyToDomainModel(ProdutoRequestDTO request, Produto produto) {
        modelMapper.map(request, produto);
    }
}
