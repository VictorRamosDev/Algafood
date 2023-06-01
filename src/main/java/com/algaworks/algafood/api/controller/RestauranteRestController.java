package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteRestController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = restauranteService.listar();
        System.out.println("Cozinha do primeiro restaurante é:");
        System.out.println(restaurantes.get(0).getCozinha().getNome());
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public Restaurante listarSingleton(@PathVariable("restauranteId") Long restauranteId) {
        return cadastroRestauranteService.buscarOuFalhar(restauranteId);
    }

    @PostMapping
    public Restaurante salvar(@RequestBody Restaurante restaurante) {
        try {
            long cozinhaId = restaurante.getCozinha().getId();
            Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
            restaurante.setCozinha(cozinha);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

        return restauranteRepository.save(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable("restauranteId") Long restauranteId, @RequestBody Restaurante entityDTO)
            throws RuntimeException{
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        BeanUtils.copyProperties(entityDTO, restauranteAtual, "id", "formasPagamento", "dataCadastro");

        try {
            Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(entityDTO.getCozinha().getId());
            restauranteAtual.setCozinha(cozinha);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

        return restauranteRepository.save(restauranteAtual);
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(
            @PathVariable("restauranteId") Long restauranteId,
            @RequestBody Map<String, Object> campos
    ) {
        Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);

        merge(campos, restauranteAtual);
        atualizar(restauranteId, restauranteAtual);
        return ResponseEntity.ok(restauranteAtual);
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Void> delete(@PathVariable("restauranteId") Long restauranteId) throws RuntimeException {
        try {
            cadastroRestauranteService.excluir(restauranteId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteAtual) {
        ObjectMapper mapper = new ObjectMapper();
        Restaurante restauranteOrigem = mapper.convertValue(campos, Restaurante.class);

        campos.forEach((nomeCampo, valorCampo) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomeCampo);
            if (field != null) {
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteAtual, novoValor);
            }
        });
    }
}
