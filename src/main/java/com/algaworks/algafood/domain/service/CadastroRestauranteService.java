package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CadastroRestauranteService {

    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante, de código %d, não pode ser removido, pois está em uso.";

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        try {
            long cozinhaId = restaurante.getCozinha().getId();
            Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

            long cidadeId = restaurante.getEndereco().getCidade().getId();
            Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

            restaurante.setCozinha(cozinha);
            restaurante.getEndereco().setCidade(cidade);

            Set<FormaPagamento> formaPagamentoList = new HashSet<>(formaPagamentoRepository.findAll());
            restaurante.setFormasPagamento(formaPagamentoList);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
            restauranteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_RESTAURANTE_EM_USO, restauranteId)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).orElseThrow(() ->
                new RestauranteNaoEncontradoException(restauranteId)
        );
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);

        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);

        restauranteAtual.inativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.addFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abreRestaurante(Restaurante restaurante) {
        restaurante.abrir();
    }

    @Transactional
    public void fechaRestaurante(Restaurante restaurante) {
        restaurante.fechar();
    }

    public Set<Usuario> listResponsaveis(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        return restaurante.getResponsaveis();
    }

    @Transactional
    public void associaResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        restaurante.associaResponsavel(usuario);
    }

    @Transactional
    public void desassociaResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        restaurante.desassociaResponsaveis(usuario);
    }
}
