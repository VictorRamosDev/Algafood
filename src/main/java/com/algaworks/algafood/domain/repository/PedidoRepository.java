package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Victor Ramos
 */

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha join fetch r.endereco e " +
            "join fetch e.cidade c join fetch c.estado")
    List<Pedido> findAll();
}
