package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeRestController {

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
    public Cidade salvar(@Valid @RequestBody Cidade cidade) {
        return cadastroCidadeService.salvar(cidade);
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable("cidadeId") Long cidadeId, @Valid @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

        try {
            Estado estado = cadastroEstadoService.buscarOuFalhar(cidade.getEstado().getId());
            cidadeAtual.setEstado(estado);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cadastroCidadeService.salvar(cidadeAtual);
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
