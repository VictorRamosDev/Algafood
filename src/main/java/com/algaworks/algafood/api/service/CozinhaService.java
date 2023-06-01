package com.algaworks.algafood.api.service;

import com.algaworks.algafood.api.model.CozinhaXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Cozinha> list() {
        return cozinhaRepository.findAll();
    }

    public CozinhaXmlWrapper listXmlWrapper() {
        return new CozinhaXmlWrapper(cozinhaRepository.findAll());
    }

    public Cozinha getSpecific(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId).orElse(null);
    }

    public ResponseEntity<Cozinha> update(Long cozinhaId, Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElse(null);

        if (cozinhaAtual != null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

            cozinhaAtual = cozinhaRepository.save(cozinhaAtual);
            return ResponseEntity.ok(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> delete(Long cozinhaId) {
        Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElse(null);
        if (cozinhaAtual != null) {
            cozinhaRepository.deleteById(cozinhaId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Cozinha> save(Cozinha cozinha) {
        Cozinha cozinhaSalva = cozinhaRepository.save(cozinha);
        return ResponseEntity.ok(cozinhaSalva);
    }

}
