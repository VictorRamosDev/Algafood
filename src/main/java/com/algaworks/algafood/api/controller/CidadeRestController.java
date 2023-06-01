package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeRestController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listCidades() {
        return cadastroCidadeService.list();
    }

    @GetMapping("/{cidadeId}")
    public Cidade getCidade(@PathVariable("cidadeId") Long cidadeId) {
        return cadastroCidadeService.getSingleton(cidadeId);
    }

    @PostMapping
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade) {
        return ResponseEntity.ok(cadastroCidadeService.salvar(cidade));
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade) {
        return cadastroCidadeService.atualizar(cidadeId, cidade);
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Void> delete(@PathVariable("cidadeId") Long cidadeId) throws RuntimeException {
        try {
            cadastroCidadeService.remover(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
