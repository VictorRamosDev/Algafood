//package com.algaworks.algafood.infrastructure;
//
//import com.algaworks.algafood.domain.model.Cidade;
//import com.algaworks.algafood.domain.repository.CidadeRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Component
//public class CidadeRepositoryImpl implements CidadeRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Cidade> listar() {
//        TypedQuery<Cidade> query = entityManager.createQuery("from Cidade c", Cidade.class);
//        return query.getResultList();
//    }
//
//    public Cidade buscar(Long id) {
//        return entityManager.find(Cidade.class, id);
//    }
//
//    @Transactional
//    public Cidade salvar(Cidade cidade) {
//        return entityManager.merge(cidade);
//    }
//
//    @Transactional
//    public void remover(Long cidadeId) {
//        Cidade cidade = buscar(cidadeId);
//        entityManager.remove(cidade);
//    }
//}
