package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Victor Ramos
 */
@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoRestController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoDtoAssembler grupoDtoAssembler;

    @GetMapping
    public List<GrupoDTO> list(@PathVariable Long usuarioId) {
        return grupoDtoAssembler.toCollectionModel(cadastroUsuarioService.listGrupos(usuarioId));
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associaGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associaGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociaGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociaGrupo(usuarioId, grupoId);
    }
}
