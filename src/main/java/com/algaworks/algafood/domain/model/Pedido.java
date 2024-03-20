package com.algaworks.algafood.domain.model;

import lombok.Data;

import javax.persistence.*;

import com.algaworks.algafood.domain.exception.NegocioException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codigo;

    private BigDecimal subTotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(value = EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> pedidoItens = new ArrayList<>();

    public void calculaValorTotal() {
        this.setDataCriacao(OffsetDateTime.now());
        this.getPedidoItens().forEach(ItemPedido::calculaPrecoTotal);

        this.subTotal = this.getPedidoItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subTotal.add(this.taxaFrete);
    }
    
    private void setStatus(StatusPedido status) {
    	if (this.getStatus().naoPodeAlterarStatusPara(status)) {
    		throw new NegocioException(
					String.format(
							"O status do pedido %s não pode ser alterado de %s para %s.", 
							this.getCodigo(), 
							this.getStatus().getDescricao(), 
							status.getDescricao()
					)
			);
    	}
    	
    	this.status = status;
    }
    
    public void confirma() {
    	this.setStatus(StatusPedido.CONFIRMADO);
		this.setDataConfirmacao(OffsetDateTime.now());
    }
    
    public void entrega() {
    	this.setStatus(StatusPedido.ENTREGUE);
		this.setDataEntrega(OffsetDateTime.now());
    }
    
    public void cancela() {
    	this.setStatus(StatusPedido.CANCELADO);
		this.setDataCancelamento(OffsetDateTime.now());
    }
    
    @PrePersist
    private void geraCodigo() {
    	this.setCodigo(UUID.randomUUID().toString());
    }

}
