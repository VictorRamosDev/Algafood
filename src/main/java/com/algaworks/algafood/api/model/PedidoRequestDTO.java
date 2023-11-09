package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class PedidoRequestDTO {

    @Valid
    @NotNull
    private RestauranteIdRequestDTO restaurante;

    @Valid
    @NotNull
    private EnderecoRequestDTO enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIdRequestDTO formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoRequestDTO> itens;

}
