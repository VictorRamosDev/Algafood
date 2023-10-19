package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Victor Ramos
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastroUsuarioService {

    private String MSG_USUARIO_EM_USO = "Usuário, de código %d, não pode ser removido, pois está em uso.";

    private final CadastroGrupoService cadastroGrupoService;

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> list() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new UsuarioNotFoundException(usuarioId)
        );
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioOpt.isPresent() && !usuarioOpt.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o email %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(MSG_USUARIO_EM_USO, usuarioId);
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNotFoundException(usuarioId);
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(senhaNova);
    }

    public Set<Grupo> listGrupos(Long usuarioId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        return usuario.getGrupos();
    }

    @Transactional
    public void associaGrupo(Long usuarioId, Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        Usuario usuario = buscarOuFalhar(usuarioId);

        usuario.associaGrupo(grupo);
    }

    @Transactional
    public void desassociaGrupo(Long usuarioId, Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        Usuario usuario = buscarOuFalhar(usuarioId);

        usuario.desassociaGrupo(grupo);
    }
}
