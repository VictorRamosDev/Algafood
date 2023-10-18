package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.disassembler.GrupoRequestDtoDisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.GrupoRequestDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
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
@RequestMapping("/grupos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GrupoRestController {

    private final CadastroGrupoService cadastroGrupoService;

    private final GrupoDtoAssembler grupoAssembler;

    private final GrupoRequestDtoDisassembler grupoDisassembler;

    @GetMapping
    public List<GrupoDTO> list() {
        List<Grupo> grupos = cadastroGrupoService.list();
        return grupoAssembler.toCollectionModel(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO getSingle(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        return grupoAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO save(@Valid @RequestBody GrupoRequestDTO request) {
        Grupo grupo = grupoDisassembler.toDomainModel(request);
        grupo = cadastroGrupoService.save(grupo);
        return grupoAssembler.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO update(@PathVariable Long grupoId, @Valid @RequestBody GrupoRequestDTO request) {
        Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
        grupoDisassembler.copyToDomainModel(request, grupoAtual);
        Grupo grupoUpdated = cadastroGrupoService.save(grupoAtual);
        return grupoAssembler.toModel(grupoUpdated);
    }

    @DeleteMapping("/{grupoId}")
    public void delete(@PathVariable Long grupoId) {
        cadastroGrupoService.delete(grupoId);
    }

}
