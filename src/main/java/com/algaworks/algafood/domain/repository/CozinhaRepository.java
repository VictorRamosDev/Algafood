package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

//    List<Cozinha> listar();
//    List<Cozinha> listarPorNome(String nome);
//    Cozinha buscar(Long id);
//    Cozinha salvar(Cozinha cozinha);
//    void remover(Long cozinhaId);

    List<Cozinha> findByNomeContaining(String nome);
}
