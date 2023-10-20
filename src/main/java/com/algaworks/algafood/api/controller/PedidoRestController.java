package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoRequestDTO;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Victor Ramos
 */

@RestController
@RequestMapping("/pedidos")
public class PedidoRestController {

    @Autowired
    private CadastroPedidoService cadastroPedidoService;

    @Autowired
    private PedidoDtoAssembler pedidoDtoAssembler;

    @GetMapping
    public List<PedidoDTO> list() {
        return pedidoDtoAssembler.toCollectionModel(cadastroPedidoService.list());
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO getSingle(@PathVariable Long pedidoId) {
        return pedidoDtoAssembler.toModel(cadastroPedidoService.buscarOuFalhar(pedidoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void salvar(@RequestBody @Valid PedidoRequestDTO request) {

    }
}
