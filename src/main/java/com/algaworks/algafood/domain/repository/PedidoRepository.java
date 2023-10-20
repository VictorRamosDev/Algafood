package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Victor Ramos
 */

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
