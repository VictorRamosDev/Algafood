package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.disassembler.EstadoRequestDtoDisassembler;
import com.algaworks.algafood.api.model.EstadoRequestDTO;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso.";

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoRequestDtoDisassembler estadoRequestDtoDisassembler;

    public List<Estado> list() {
        return estadoRepository.findAll();
    }

    public Estado getSingleton(Long estadoId) {
        return buscarOuFalhar(estadoId);
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public Estado atualizar(Long estadoId, EstadoRequestDTO request) {
        Estado estadoAtual = buscarOuFalhar(estadoId);

        estadoRequestDtoDisassembler.copyToDomainModel(request, estadoAtual);
//        BeanUtils.copyProperties(estadoAtual, estadoAtual, "id");

        return salvar(estadoAtual);
    }

    @Transactional
    public void remover(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(estadoId);
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}
