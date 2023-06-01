package com.algaworks.algafood.api.service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listEstados() {
        return estadoRepository.findAll();
    }

    public Estado getEstado(Long estadoId) {
        return estadoRepository.findById(estadoId).orElse(null);
    }
}
