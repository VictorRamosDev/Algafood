package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> list() {
        return cidadeRepository.findAll();
    }

    public Cidade getSingleton(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Cidade de código %d não encontrado.", cidadeId)
                )
        );
    }

    public Cidade salvar(Cidade cidade) {
        Estado estado = cidade.getEstado();
        if (estado != null) {
            Estado estadoExistente = estadoRepository.findByNome(estado.getNome()).orElse(null);
            if (estadoExistente == null) {
                estadoExistente = estadoRepository.save(estado);
                cidade.setEstado(estadoExistente);
            }
        }
        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("A cidade de código %d não foi encontrado.", cidadeId)
        ));

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        return cidadeRepository.save(cidadeAtual);
    }

    public void remover(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser removido, pois está em uso.", cidadeId)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Cidade de código %d não foi encontrado.", cidadeId)
            );
        }
    }
}
