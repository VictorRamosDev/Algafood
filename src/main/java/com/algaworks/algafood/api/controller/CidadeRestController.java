package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.disassembler.CidadeRequestDtoDisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.CidadeRequestDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CidadeRestController {

    private final CadastroCidadeService cadastroCidadeService;

    private final CidadeDtoAssembler cidadeDtoAssembler;

    private final CidadeRequestDtoDisassembler cidadeRequestDtoDisassembler;

    @GetMapping
    public List<CidadeDTO> listCidades() {
        return cidadeDtoAssembler.toCollectionModel(cadastroCidadeService.list());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO getCidade(@PathVariable("cidadeId") Long cidadeId) {
        return cidadeDtoAssembler.toModel(cadastroCidadeService.getSingleton(cidadeId));
    }

    @PostMapping
    public CidadeDTO salvar(@Valid @RequestBody CidadeRequestDTO request) {
        Cidade cidade = cidadeRequestDtoDisassembler.toDomainModel(request);
        cidade = cadastroCidadeService.salvar(cidade);
        return cidadeDtoAssembler.toModel(cidade);
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable("cidadeId") Long cidadeId, @Valid @RequestBody CidadeRequestDTO request) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);
//            Estado estado = cadastroEstadoService.buscarOuFalhar(request.getEstado().getId());
//            cidadeAtual.setEstado(estado);
//            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            cidadeRequestDtoDisassembler.copyToDomainModel(request, cidadeAtual);

            return cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
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
