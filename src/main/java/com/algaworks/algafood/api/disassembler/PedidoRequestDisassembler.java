package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.PedidoRequestDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Victor Ramos
 */

@Component
public class PedidoRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainModel(PedidoRequestDTO request) {
        return modelMapper.map(request, Pedido.class);
    }

}
