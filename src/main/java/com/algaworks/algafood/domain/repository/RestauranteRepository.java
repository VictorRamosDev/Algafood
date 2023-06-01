package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryCustom,
        JpaSpecificationExecutor<Restaurante> {

//    List<Restaurante> listar();
//    Restaurante buscar(Long id);
//    Restaurante salvar(Restaurante restaurante);
//    void remover(Long restauranteId);

    @Query("from Restaurante r join fetch r.cozinha join fetch r.formasPagamento group by r.id")
    List<Restaurante> findAll();

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    @Query("from Restaurante where nome like %:restauranteNome% and cozinha.id = :id")
    List<Restaurante> findByName(String restauranteNome, @Param("id") Long cozinhaId);

//    List<Restaurante> findByNomeContainingAndCozinhaId(String restauranteNome, Long cozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    Long countByCozinhaId(Long cozinhaId);

    List<Restaurante> findTop2ByNomeContaining(String restauranteNome);

//    @Query("from Restaurante where cozinha.id = :cozinhaId and taxaFrete between :taxaInicial and :taxaFinal")
    List<Restaurante> queryByCozinhaIdAndTaxaFrete(
            Long cozinhaId,
            BigDecimal taxaInicial,
            BigDecimal taxaFinal,
            String cozinhaNome
    );

}
