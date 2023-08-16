package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
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
public class FormaPagamentoAssembler {

    private final ModelMapper modelMapper;


    public List<FormaPagamentoDTO> toCollectionModel(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream().map(this::toModel).collect(Collectors.toList());
    }

    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }
}
