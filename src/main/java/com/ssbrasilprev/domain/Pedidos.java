package com.ssbrasilprev.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="pedidos")
@ApiModel(description = "Todos os detalhes sobre o Pedido.")
public class Pedidos {

    @Id
    @GeneratedValue
    @Column(name="idPedido")
    private Long idPedido;
	
    @OneToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

	@Column(name="data")
    private Date data;

    @Column(name="status")
    private String status;
    
    @Column(name="sessao")
    private String sessao;
	
	public Pedidos(Long idPedido, Clientes cliente, Date data, String status, String sessao) {
		this.idPedido = idPedido;
		this.cliente = cliente;
		this.data = data;
		this.status = status;
		this.sessao = sessao;
	}
	public Pedidos(Clientes cliente, Date data, String status, String sessao) {
		this.cliente = cliente;
		this.data = data;
		this.status = status;
		this.sessao = sessao;
	}
	
	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSessao() {
		return sessao;
	}

	public void setSessao(String sessao) {
		this.sessao = sessao;
	}

	 
	@Override
    public String toString() {
        return "Pedidos{" +
                "idPedido=" + idPedido +
                ", cliente='" + cliente + '\'' +
                '}';
    }

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Pedidos() {
	}

}
