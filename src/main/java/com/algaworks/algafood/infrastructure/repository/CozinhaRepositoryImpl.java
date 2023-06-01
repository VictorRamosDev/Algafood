//package com.algaworks.algafood.infrastructure;
//
//import com.algaworks.algafood.domain.model.Cozinha;
//import com.algaworks.algafood.domain.repository.CozinhaRepository;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Repository
//public class CozinhaRepositoryImpl implements CozinhaRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Cozinha> listar() {
//        TypedQuery<Cozinha> query = entityManager.createQuery("from Cozinha c", Cozinha.class);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Cozinha> listarPorNome(String nome) {
//        return entityManager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
//                .setParameter("nome", "%" + nome + "%")
//                .getResultList();
//    }
//
//    public Cozinha buscar(Long id) {
//        return entityManager.find(Cozinha.class, id);
//    }
//
//    @Transactional
//    public Cozinha salvar(Cozinha cozinha) {
//        return entityManager.merge(cozinha);
//    }
//
//    @Transactional
//    public void remover(Long cozinhaId) {
//        Cozinha cozinha = buscar(cozinhaId);
//
//        if (cozinha == null) {
//            throw new EmptyResultDataAccessException(1);
//        }
//
//        entityManager.remove(cozinha);
//    }
//}
