package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class PedidoTinyDTO {

    private String codigo;

    private BigDecimal subTotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private RestauranteTinyDTO restaurante;

    private UsuarioDTO cliente;

    private StatusPedido status;

}
