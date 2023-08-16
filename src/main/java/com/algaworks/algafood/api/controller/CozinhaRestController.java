package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaRequestDtoDisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.CozinhaRequestDTO;
import com.algaworks.algafood.api.model.CozinhaXmlWrapper;
import com.algaworks.algafood.api.service.CozinhaService;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CozinhaRestController {

    private final CozinhaService cozinhaService;

    private final CadastroCozinhaService cadastroCozinhaService;

    private final CozinhaDtoAssembler cozinhaDtoAssembler;

    private final CozinhaRequestDtoDisassembler cozinhaRequestDtoDisassembler;

    @GetMapping
    public List<CozinhaDTO> listCozinhas() {
        return cozinhaDtoAssembler.toCollectionModel(cozinhaService.list());
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhaXmlWrapper listXmlCozinhas() {
        return cozinhaService.listXmlWrapper();
    }

    @GetMapping(value = "/{cozinhaId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public CozinhaDTO getSpecific(@PathVariable Long cozinhaId) {
        return cozinhaDtoAssembler.toModel(cadastroCozinhaService.buscarOuFalhar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO save(@Valid @RequestBody CozinhaRequestDTO request) {
        Cozinha cozinha = cozinhaRequestDtoDisassembler.toDomainModel(request);
        cozinha = cadastroCozinhaService.salvar(cozinha);
        return cozinhaDtoAssembler.toModel(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO update(
            @PathVariable("cozinhaId") Long cozinhaId,
            @Valid @RequestBody CozinhaRequestDTO request
    ) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
//        BeanUtils.copyProperties(request, cozinhaAtual, "id");
        cozinhaRequestDtoDisassembler.copyToDomainModel(request, cozinhaAtual);

        return cozinhaDtoAssembler.toModel(cadastroCozinhaService.salvar(cozinhaAtual));
    }

//    @DeleteMapping("/{cozinhaId}")
//    public ResponseEntity<Void> delete(@PathVariable("cozinhaId") Long cozinhaId) throws RuntimeException {
//        try {
//            cadastroCozinhaService.excluir(cozinhaId);
//            return ResponseEntity.noContent().build();
//        } catch (EntidadeNaoEncontradaException e) {
//            return ResponseEntity.notFound().build();
//        } catch (EntidadeEmUsoException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cozinhaId") Long cozinhaId) {
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
