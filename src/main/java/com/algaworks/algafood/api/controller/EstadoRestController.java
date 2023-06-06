package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoRestController {

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listEstados() {
        return cadastroEstadoService.list();
    }

    @GetMapping("/{estadoId}")
    public Estado getEstado(@PathVariable("estadoId") Long estadoId) {
        return cadastroEstadoService.getSingleton(estadoId);
    }

    @PostMapping
    public Estado salvar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable("estadoId") Long estadoId, @RequestBody Estado estado) {
        return cadastroEstadoService.atualizar(estadoId, estado);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("estadoId") Long estadoId) {
        cadastroEstadoService.remover(estadoId);
    }
}
