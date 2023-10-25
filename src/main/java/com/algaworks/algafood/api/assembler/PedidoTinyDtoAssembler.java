package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoTinyDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victor Ramos
 */

@Component
public class PedidoTinyDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoTinyDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoTinyDTO.class);
    }

    public List<PedidoTinyDTO> toCollectionModel(Collection<Pedido> pedidos) {
        return pedidos.stream().map(this::toModel).collect(Collectors.toList());
    }
}
