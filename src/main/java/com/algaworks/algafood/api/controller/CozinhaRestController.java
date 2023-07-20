package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhaXmlWrapper;
import com.algaworks.algafood.api.service.CozinhaService;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaRestController {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listCozinhas() {
        return cozinhaService.list();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhaXmlWrapper listXmlCozinhas() {
        return cozinhaService.listXmlWrapper();
    }

    @GetMapping(value = "/{cozinhaId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Cozinha getSpecific(@PathVariable Long cozinhaId) {
        return cadastroCozinhaService.buscarOuFalhar(cozinhaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha save(@Valid @RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha update(@PathVariable("cozinhaId") Long cozinhaId, @Valid @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cadastroCozinhaService.salvar(cozinhaAtual);
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
