package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoRequestDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.FormaPagamentoRequestDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Victor Ramos
 */

@RestController
@RequestMapping("/forma-pagamentos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormaPagamentoRestController {

    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;

    private final FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

    private final FormaPagamentoRequestDisassembler formaPagamentoRequestDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return formaPagamentoDtoAssembler.toCollectionModel(cadastroFormaPagamentoService.listar());
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO getSingle(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        return formaPagamentoDtoAssembler.toModel(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@Valid @RequestBody FormaPagamentoRequestDTO request) {
        FormaPagamento formaPagamento = formaPagamentoRequestDisassembler.toDomainModel(request);
        formaPagamento = cadastroFormaPagamentoService.salvar(formaPagamento);
        return formaPagamentoDtoAssembler.toModel(formaPagamento);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId, @Valid @RequestBody FormaPagamentoRequestDTO request) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
        formaPagamentoRequestDisassembler.copyToDomainModel(request, formaPagamentoAtual);
        return formaPagamentoDtoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
    }

    @DeleteMapping("/{formaPagamentoId}")
    public void delete(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.delete(formaPagamentoId);
    }
}
