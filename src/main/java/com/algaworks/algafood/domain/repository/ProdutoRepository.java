package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Victor Ramos
 */

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    List<Produto> findByRestauranteId(Long restauranteId);

    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);
}
