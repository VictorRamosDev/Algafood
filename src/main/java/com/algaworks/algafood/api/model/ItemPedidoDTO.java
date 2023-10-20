package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class ItemPedidoDTO {

    private Long id;

    private int quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private String observacao;

    private Long produtoId;

    private String produtoNome;
}
