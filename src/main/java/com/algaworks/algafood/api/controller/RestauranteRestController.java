package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    private SmartValidator validator;

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
    public Restaurante salvar(@Valid @RequestBody Restaurante restaurante) {
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
    public Restaurante atualizar(
            @PathVariable("restauranteId") Long restauranteId,
            @Valid @RequestBody Restaurante restaurante
    ) throws RuntimeException {
        try {
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro");

            Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
            restauranteAtual.setCozinha(cozinha);
            return restauranteRepository.save(restauranteAtual);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(
            @PathVariable("restauranteId") Long restauranteId,
            @RequestBody Map<String, Object> campos,
            HttpServletRequest servletRequest
    ) {
        Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);

        merge(campos, restauranteAtual, servletRequest);
        validate(restauranteAtual, "restaurante");
        atualizar(restauranteId, restauranteAtual);

        return ResponseEntity.ok(restauranteAtual);
    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bidingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bidingResult);

        if (bidingResult.hasErrors()) {
            throw new ValidacaoException(bidingResult);
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restauranteId") Long restauranteId) {
        cadastroRestauranteService.excluir(restauranteId);
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteAtual, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        ServletServerHttpRequest serverRequest = new ServletServerHttpRequest(request);

        try {
            Restaurante restauranteOrigem = mapper.convertValue(campos, Restaurante.class);

            campos.forEach((nomeCampo, valorCampo) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomeCampo);
                if (field != null) {
                    field.setAccessible(true);
                    Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                    ReflectionUtils.setField(field, restauranteAtual, novoValor);
                }
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverRequest);
        }
    }
}
