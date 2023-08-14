package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.CidadeRequestDTO;
import com.algaworks.algafood.domain.model.Cidade;
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
public class CidadeRequestDtoDisassembler {

    private final ModelMapper modelMapper;

    public Cidade toDomainModel(CidadeRequestDTO request) {
        return modelMapper.map(request, Cidade.class);
    }

    public void copyToDomainModel(CidadeRequestDTO request, Cidade cidade) {
        // Para evitar o erro: Caused by: org.hibernate.HibernateException:
        // identifier of an instance of com.algaworks.algafood.domain.model.Estado was altered from 3 to 2
        cidade.setEstado(new Estado());

        modelMapper.map(request, cidade);
    }
}
