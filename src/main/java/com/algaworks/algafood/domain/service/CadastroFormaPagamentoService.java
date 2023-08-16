package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Victor Ramos
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastroFormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;


    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        Optional<FormaPagamento> formaPagamentoOpt = formaPagamentoRepository.findById(formaPagamentoId);

        return formaPagamentoOpt.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public void delete(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
        } catch (DataIntegrityViolationException e) {
            throw new FormaPagamentoEmUsoException(formaPagamentoId);
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        }
    }
}
