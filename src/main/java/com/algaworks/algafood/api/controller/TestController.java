package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public ResponseEntity<List<Cozinha>> listarCozinhaPorNome(@RequestParam String cozinhaNome) {
        return ResponseEntity.ok(cozinhaRepository.findByNomeContaining(cozinhaNome));
    }

    @GetMapping("/restaurantes/por-nome")
    public ResponseEntity<List<Restaurante>> listarRestaurantePorNome(
            @RequestParam String restauranteNome,
            @RequestParam Long cozinhaId
    ) {
        return ResponseEntity.ok(restauranteRepository.findByName(restauranteNome, cozinhaId));
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public ResponseEntity<Restaurante> buscarPrimeiroRestaurantePorNome(
            @RequestParam String restauranteNome
    ) {
        return ResponseEntity.ok(restauranteRepository.findFirstRestauranteByNomeContaining(restauranteNome).orElse(null));
    }

    @GetMapping("/restaurantes/taxa-frete-between")
    public ResponseEntity<List<Restaurante>> listarRestaurantesPorTaxaFrete(
            @RequestParam BigDecimal taxaInicial,
            @RequestParam BigDecimal taxaFinal
    ) {
        return ResponseEntity.ok(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
    }

    @GetMapping("/restaurantes/cozinha-taxa-frete-between")
    public ResponseEntity<List<Restaurante>> listarRestaurantesPorCozinhaTaxaFrete(
            @RequestParam Long cozinhaId,
            @RequestParam BigDecimal taxaInicial,
            @RequestParam BigDecimal taxaFinal,
            @RequestParam String cozinhaNome
    ) {
        var restaurantes = restauranteRepository
                .queryByCozinhaIdAndTaxaFrete(cozinhaId, taxaInicial, taxaFinal, cozinhaNome);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/restaurantes/nome-taxa-frete")
    public ResponseEntity<List<Restaurante>> listarRestaurantesNomeTaxaFrete(
            @RequestParam(required = false) String restauranteNome,
            @RequestParam(required = false) BigDecimal taxaInicial,
            @RequestParam(required = false) BigDecimal taxaFinal
    ) {
        var restaurantes = restauranteRepository.find(restauranteNome, taxaInicial, taxaFinal);
        return ResponseEntity.ok(restaurantes);
    }

    @ResponseBody
    @GetMapping("/restaurantes/taxa-frete-gratis")
    public List<Restaurante> listarRestaurantesNomeTaxaFrete(@RequestParam(required = false) String restauranteNome) {
        return restauranteRepository.findComFreteGratis(restauranteNome);
    }

    @ResponseBody
    @GetMapping("/restaurantes/buscar-primeiro")
    public Restaurante buscarPrimeiroRestaurante() {
        return restauranteRepository.buscarPrimeiro().orElse(null);
    }
}
