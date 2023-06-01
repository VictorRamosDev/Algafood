package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

    Optional<Estado> findByNome(String nome);

//    List<Estado> listar();
//    Estado buscar(Long id);
//    Estado salvar(Estado estado);
//    void remover(Long estadoId);
}
