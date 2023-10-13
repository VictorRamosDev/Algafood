package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioRequestDtoDisassembler;
import com.algaworks.algafood.api.model.SenhaRequestDTO;
import com.algaworks.algafood.api.model.UsuarioComSenhaRequestDTO;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.UsuarioRequestDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioRestController {

    private final CadastroUsuarioService cadastroUsuarioService;

    private final UsuarioDtoAssembler usuarioDtoAssembler;

    private final UsuarioRequestDtoDisassembler usuarioRequestDtoDisassembler;

    @GetMapping
    public List<UsuarioDTO> list() {
        List<Usuario> usuarios = cadastroUsuarioService.list();
        return usuarioDtoAssembler.toCollectionModel(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO getSingle(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return usuarioDtoAssembler.toModel(usuario);
    }

    @PostMapping
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioRequestDTO request) {
        Usuario usuario = usuarioRequestDtoDisassembler.toDomainModel(request);
        usuario = cadastroUsuarioService.salvar(usuario);
        return usuarioDtoAssembler.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioRequestDTO request) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        usuarioRequestDtoDisassembler.copyToDomainModel(request, usuarioAtual);
        usuarioAtual = cadastroUsuarioService.salvar(usuarioAtual);
        return usuarioDtoAssembler.toModel(usuarioAtual);
    }

    @PostMapping("/salvar-com-senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UsuarioDTO createWithPassword(@RequestBody @Valid UsuarioComSenhaRequestDTO request) {
        Usuario usuario = usuarioRequestDtoDisassembler.toDomainModel(request);
        usuario = cadastroUsuarioService.salvar(usuario);
        return usuarioDtoAssembler.toModel(usuario);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaRequestDTO senha) {
        cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long usuarioId) {
        cadastroUsuarioService.delete(usuarioId);
    }

}
