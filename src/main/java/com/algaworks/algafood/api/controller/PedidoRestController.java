package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoTinyDtoAssembler;
import com.algaworks.algafood.api.disassembler.PedidoRequestDisassembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoRequestDTO;
import com.algaworks.algafood.api.model.PedidoTinyDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PedidoTinyDtoAssembler pedidoTinyDtoAssembler;

    @Autowired
    private PedidoRequestDisassembler pedidoRequestDisassembler;

    @GetMapping
    public List<PedidoTinyDTO> list() {
        return pedidoTinyDtoAssembler.toCollectionModel(cadastroPedidoService.list());
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO getSingle(@PathVariable Long pedidoId) {
        return pedidoDtoAssembler.toModel(cadastroPedidoService.buscarOuFalhar(pedidoId));
    }

    @PostMapping
    public PedidoDTO salvar(@RequestBody @Valid PedidoRequestDTO request) {
        try {
            Pedido pedidoNovo = pedidoRequestDisassembler.toDomainModel(request);
            Usuario usuario = new Usuario();
            usuario.setId(1L);
            pedidoNovo.setCliente(usuario);

            pedidoNovo = cadastroPedidoService.emite(pedidoNovo);

            return pedidoDtoAssembler.toModel(pedidoNovo);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
