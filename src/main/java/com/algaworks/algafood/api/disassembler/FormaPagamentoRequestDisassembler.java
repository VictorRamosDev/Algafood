package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.FormaPagamentoRequestDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormaPagamentoRequestDisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamento toDomainModel(FormaPagamentoRequestDTO request) {
        return modelMapper.map(request, FormaPagamento.class);
    }

    public void copyToDomainModel(FormaPagamentoRequestDTO request, FormaPagamento formaPagamento) {
        modelMapper.map(request, formaPagamento);
    }
}
