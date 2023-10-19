package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Victor Ramos
 */

@RestController
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioRestController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioDtoAssembler usuarioDtoAssembler;

    @GetMapping
    public List<UsuarioDTO> list(@PathVariable Long restauranteId) {
        return usuarioDtoAssembler.toCollectionModel(cadastroRestauranteService.listResponsaveis(restauranteId));
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associaResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.associaResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociaResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.desassociaResponsavel(restauranteId, usuarioId);
    }
}
