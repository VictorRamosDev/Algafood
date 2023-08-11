package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.CozinhaIdRequestDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.RestauranteRequestDTO;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
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
import java.util.stream.Collectors;

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
    public List<RestauranteDTO> listar() {
//        System.out.println("Cozinha do primeiro restaurante é:");
//        System.out.println(restaurantes.get(0).getCozinha().getNome());
        return toCollectionModel(restauranteService.listar());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO listarSingleton(@PathVariable("restauranteId") Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO salvar(@Valid @RequestBody RestauranteRequestDTO request) {
        Restaurante restaurante = toDomainModel(request);
        return toModel(cadastroRestauranteService.salvar(restaurante));
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(
            @PathVariable("restauranteId") Long restauranteId,
            @Valid @RequestBody RestauranteRequestDTO request
    ) throws RuntimeException {
        try {
            Restaurante restaurante = toDomainModel(request);
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro");

            Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
            restauranteAtual.setCozinha(cozinha);
            return toModel(restauranteRepository.save(restauranteAtual));
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

        if (restauranteAtual != null) {
            merge(campos, restauranteAtual, servletRequest);
            validate(restauranteAtual, "restaurante");
            RestauranteRequestDTO restauranteRequestDTO = toRequestModel(restauranteAtual);
            RestauranteDTO restauranteDTO = atualizar(restauranteId, restauranteRequestDTO);

            return ResponseEntity.ok(restauranteDTO);
        }
        return null;
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

    private RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO dto = new RestauranteDTO();
        dto.setId(restaurante.getId());
        dto.setNome(restaurante.getNome());
        dto.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());
        dto.setCozinha(cozinhaDTO);

        return dto;
    }

    private List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).collect(Collectors.toList());
    }

    private Restaurante toDomainModel(RestauranteRequestDTO request) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(request.getNome());
        restaurante.setTaxaFrete(request.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(request.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }

    private RestauranteRequestDTO toRequestModel(Restaurante restaurante) {
        RestauranteRequestDTO restauranteRequestDTO = new RestauranteRequestDTO();
        restauranteRequestDTO.setNome(restaurante.getNome());
        restauranteRequestDTO.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIdRequestDTO cozinhaIdRequestDTO = new CozinhaIdRequestDTO();
        cozinhaIdRequestDTO.setId(restaurante.getCozinha().getId());
        restauranteRequestDTO.setCozinha(cozinhaIdRequestDTO);

        return restauranteRequestDTO;
    }
}
