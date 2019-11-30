package com.ssbrasilprev.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="pedidoitens")
@ApiModel(description = "Todos os detalhes sobre os Itens do Pedido.")
public class PedidoItens {

    @Id
    @GeneratedValue
    @Column(name="idItem")
    private Long idItem;

    @OneToOne
    @JoinColumn(name = "id_produto",  nullable = false)
    private Produtos produto;

	@OneToOne
    @JoinColumn(name = "id_pedido",  nullable = false)
    private Pedidos pedido;

	@Column(name="quantidade")
    private Integer quantidade;
    
    @Column(name="valor")
    private BigDecimal valor;
	
    @Column(name="subtotal")
    private BigDecimal subtotal;

	public PedidoItens(Long idItem, Produtos produto, Pedidos pedido, Integer quantidade, BigDecimal valor,
			BigDecimal subtotal) {
		this.idItem = idItem;
		this.produto = produto;
		this.pedido = pedido;
		this.quantidade = quantidade;
		this.valor = valor;
		this.subtotal = subtotal;
	}
	public PedidoItens(Produtos produto, Pedidos pedido, Integer quantidade, BigDecimal valor,
		BigDecimal subtotal) {
		this.produto = produto;
		this.pedido = pedido;
		this.quantidade = quantidade;
		this.valor = valor;
		this.subtotal = subtotal;
	}
	
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public Pedidos getPedido() {
		return pedido;
	}

	public void setPedido(Pedidos pedido) {
		this.pedido = pedido;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	 
	@Override
    public String toString() {
        return "PedidoItens{" +
                "idPedido=" + pedido.getIdPedido() +
                ", idProduto='" + produto.getIdProduto() + '\'' +
                '}';
    }

	public PedidoItens() {
	}

}
