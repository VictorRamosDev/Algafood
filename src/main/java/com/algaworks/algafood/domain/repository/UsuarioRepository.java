package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Victor Ramos
 */

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {


}
