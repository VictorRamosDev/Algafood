package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.exceptionhandler.Problema;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeRestController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Cidade> listCidades() {
        return cadastroCidadeService.list();
    }

    @GetMapping("/{cidadeId}")
    public Cidade getCidade(@PathVariable("cidadeId") Long cidadeId) {
        return cadastroCidadeService.getSingleton(cidadeId);
    }

    @PostMapping
    public Cidade salvar(@RequestBody Cidade cidade) {
        try {
            long estadoId = cidade.getEstado().getId();
            Estado estadoExistente = cadastroEstadoService.buscarOuFalhar(estadoId);
            cidade.setEstado(estadoExistente);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

        return cidadeRepository.save(cidade);
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

        try {
            Estado estado = cadastroEstadoService.buscarOuFalhar(cidade.getEstado().getId());
            cidadeAtual.setEstado(estado);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cidadeRepository.save(cidadeAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cidadeId") Long cidadeId) {
        cadastroCidadeService.remover(cidadeId);
    }

}
