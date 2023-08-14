package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.disassembler.EstadoRequestDtoDisassembler;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.EstadoRequestDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EstadoRestController {

    private final CadastroEstadoService cadastroEstadoService;

    private final EstadoDtoAssembler estadoDtoAssembler;

    private final EstadoRequestDtoDisassembler estadoRequestDtoDisassembler;

    @GetMapping
    public List<EstadoDTO> listEstados() {
        return estadoDtoAssembler.toCollectionModel(cadastroEstadoService.list());
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO getEstado(@PathVariable("estadoId") Long estadoId) {
        return estadoDtoAssembler.toModel(cadastroEstadoService.getSingleton(estadoId));
    }

    @PostMapping
    public EstadoDTO salvar(@Valid @RequestBody EstadoRequestDTO request) {
        Estado estado = estadoRequestDtoDisassembler.toDomainModel(request);
        estado = cadastroEstadoService.salvar(estado);
        return estadoDtoAssembler.toModel(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO atualizar(@PathVariable("estadoId") Long estadoId, @Valid @RequestBody EstadoRequestDTO request) {
        Estado estado = cadastroEstadoService.atualizar(estadoId, request);
        return estadoDtoAssembler.toModel(estado);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("estadoId") Long estadoId) {
        cadastroEstadoService.remover(estadoId);
    }
}
