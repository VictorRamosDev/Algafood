package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author Victor Ramos
 */

@Getter
@Setter
public class PedidoDTO {

    private String codigo;

    private BigDecimal subTotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    private FormaPagamentoDTO formaPagamento;

    private RestauranteTinyDTO restaurante;

    private UsuarioDTO cliente;

    private EnderecoDTO endereco;

    private StatusPedido status;

    private List<ItemPedidoDTO> itemPedido;
}
