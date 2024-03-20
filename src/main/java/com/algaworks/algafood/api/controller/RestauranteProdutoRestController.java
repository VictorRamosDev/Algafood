package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoDtoAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoRequestDtoDisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.ProdutoRequestDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoRestController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private ProdutoDtoAssembler produtoDtoAssembler;

    @Autowired
    private ProdutoRequestDtoDisassembler produtoRequestDtoDisassembler;

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId) {
        List<Produto> produtos = cadastroProdutoService.list(restauranteId);
        return produtoDtoAssembler.toCollectionModel(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO getSingle(@PathVariable Long restauranteId, @PathVariable("produtoId") Long produtoId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produto = cadastroProdutoService.buscarOuFalhar(restaurante.getId(), produtoId);

        return produtoDtoAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO salvar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoRequestDTO request) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoRequestDtoDisassembler.toDomainModel(request);

        produto.setRestaurante(restaurante);
        produto = cadastroProdutoService.salvar(produto);
        return produtoDtoAssembler.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(
            @PathVariable("restauranteId") Long restauranteId,
            @PathVariable("produtoId") Long produtoId,
            @Valid @RequestBody ProdutoRequestDTO request
    ){
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restaurante.getId(), produtoId);
        produtoRequestDtoDisassembler.copyToDomainModel(request, produtoAtual);

        Produto produtoUpdated = cadastroProdutoService.salvar(produtoAtual);
        return produtoDtoAssembler.toModel(produtoUpdated);
    }
}
