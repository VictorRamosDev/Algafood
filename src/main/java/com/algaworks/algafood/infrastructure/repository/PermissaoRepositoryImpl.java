//package com.algaworks.algafood.infrastructure.repository;
//
//import com.algaworks.algafood.domain.model.Permissao;
//import com.algaworks.algafood.domain.repository.PermissaoRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Component
//public class PermissaoRepositoryImpl implements PermissaoRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Permissao> listar() {
//        TypedQuery<Permissao> query = entityManager.createQuery("from Permissao p", Permissao.class);
//        return query.getResultList();
//    }
//
//    public Permissao buscar(Long id) {
//        return entityManager.find(Permissao.class, id);
//    }
//
//    @Transactional
//    public Permissao salvar(Permissao permissao) {
//        return entityManager.merge(permissao);
//    }
//
//    @Transactional
//    public void remover(Permissao permissao) {
//        permissao = buscar(permissao.getId());
//        entityManager.remove(permissao);
//    }
//}
