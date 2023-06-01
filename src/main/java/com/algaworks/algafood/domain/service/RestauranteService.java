package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante listarSingleton(Long restauranteId) {
        try {
            Restaurante restaurante = restauranteRepository.findById(restauranteId).orElse(null);
            if (restaurante != null) {
                return restauranteRepository.findById(restauranteId).orElse(null);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Restaurante de código %d não foi encontrado.", restauranteId)
            );
        }
        return null;
    }

    public Restaurante salvar(Restaurante restaurante) throws RuntimeException {
        if (restaurante != null) {
            return restauranteRepository.save(restaurante);
        }

        return null;
    }
}
