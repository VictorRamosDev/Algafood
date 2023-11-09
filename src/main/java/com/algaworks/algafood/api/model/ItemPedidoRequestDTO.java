package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class ItemPedidoRequestDTO {

    @NotNull
    private Long produtoId;

    private String observacao;

    @NotNull
    @PositiveOrZero
    private Integer quantidade;
}
