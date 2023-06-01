package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

//    List<Cidade> listar();
//    Cidade buscar(Long id);
//    Cidade salvar(Cidade cidade);
//    void remover(Long cidadeId);
}
