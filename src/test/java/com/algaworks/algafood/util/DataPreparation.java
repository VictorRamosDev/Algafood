package com.algaworks.algafood.util;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataPreparation {

    // Cozinha
    public static List<Cozinha> prepareCozinhaData() {
        List<Cozinha> cozinhaList = new ArrayList<>();

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaList.add(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Americana");
        cozinhaList.add(cozinha2);

        Cozinha cozinha3 = new Cozinha();
        cozinha3.setNome("Brasileira");
        cozinhaList.add(cozinha3);

        return cozinhaList;
    }

    public static List<Restaurante> prepareRestauranteData(CozinhaRepository cozinhaRepository) {
        List<Restaurante> restauranteList = new ArrayList<>();
        List<Cozinha> cozinhaList = prepareCozinhaData();
        cozinhaList = cozinhaRepository.saveAll(cozinhaList);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Thai Gourmet");
        restaurante1.setTaxaFrete(BigDecimal.TEN);
        restaurante1.setCozinha(cozinhaList.get(0));
        restauranteList.add(restaurante1);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("McDonalds");
        restaurante2.setTaxaFrete(BigDecimal.valueOf(15L));
        restaurante2.setCozinha(cozinhaList.get(1));
        restauranteList.add(restaurante2);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setNome("Comida Mineira - Frete Grátis");
        restaurante3.setTaxaFrete(BigDecimal.ZERO);
        restaurante3.setCozinha(cozinhaList.get(2));
        restauranteList.add(restaurante3);

        return restauranteList;
    }

    public static List<FormaPagamento> prepareFormaPagamentoData() {
        List<FormaPagamento> formasPagamento = new ArrayList<>();
        FormaPagamento formaPagamento1 = new FormaPagamento();
        formaPagamento1.setDescricao("Cartão de crédito");
        formasPagamento.add(formaPagamento1);

        FormaPagamento formaPagamento2 = new FormaPagamento();
        formaPagamento2.setDescricao("Cartão de débito");
        formasPagamento.add(formaPagamento2);


        FormaPagamento formaPagamento3 = new FormaPagamento();
        formaPagamento3.setDescricao("Dinheiro");
        formasPagamento.add(formaPagamento3);

        return formasPagamento;
    }
}
