package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    // Consulta dinâmica com Criteria (mais robusta e atual)
    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Restaurante> root = criteria.from(Restaurante.class);

        if (StringUtils.hasLength(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        Specification<Restaurante> spec = comFreteGratis();

        if (StringUtils.hasLength(nome)) {
            spec = spec.and(comNomeSemelhante(nome));
        }
        return restauranteRepository.findAll(spec);
    }

    // 5.12 - Implementando uma consulta dinâmica com JPQL (forma braçal)
//    @Override
//    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//        var jpql = new StringBuilder("from Restaurante where 0 = 0 ");
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        TypedQuery<Restaurante> query = entityManager
//                .createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach(query::setParameter);
//
//        return query.getResultList();
//    }








//    @Override
//    public List<Restaurante> listar() {
//        TypedQuery<Restaurante> typedQuery = entityManager.createQuery("from Restaurante r", Restaurante.class);
//        return typedQuery.getResultList();
//    }
//
//    @Override
//    public Restaurante buscar(Long id) {
//        return entityManager.find(Restaurante.class, id);
//    }
//
//    @Transactional
//    @Override
//    public Restaurante salvar(Restaurante restaurante) {
//        return entityManager.merge(restaurante);
//    }
//
//    @Transactional
//    @Override
//    public void remover(Long restauranteId) {
//        Restaurante restaurante = buscar(restauranteId);
//        entityManager.remove(restaurante);
//    }
}
