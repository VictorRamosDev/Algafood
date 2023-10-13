package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Victor Ramos
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastroProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> list(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    public Produto buscarOuFalhar(Restaurante restaurante, Long produtoId) {
        return produtoRepository.findByIdAndRestauranteId(produtoId, restaurante.getId()).orElseThrow(
                () -> new ProdutoNaoEncontradoException(produtoId, restaurante.getId())
        );
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
