//package com.algaworks.algafood.infrastructure;
//
//import com.algaworks.algafood.domain.model.Estado;
//import com.algaworks.algafood.domain.repository.EstadoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Component
//public class EstadoRepositoryImpl implements EstadoRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Estado> listar() {
//        TypedQuery<Estado> query = entityManager.createQuery("from Estado e", Estado.class);
//        return query.getResultList();
//    }
//
//    public Estado buscar(Long id) {
//        return entityManager.find(Estado.class, id);
//    }
//
//    @Transactional
//    public Estado salvar(Estado estado) {
//        return entityManager.merge(estado);
//    }
//
//    @Transactional
//    public void remover(Long estadoId) {
//        Estado estado = buscar(estadoId);
//        entityManager.remove(estado);
//    }
//}
