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
@Table(name="produtos")
@ApiModel(description = "Todos os detalhes sobre o Produto.")
public class Produtos {

    @Id
    @GeneratedValue
    @Column(name="idProduto")
    private Long idProduto;

    @OneToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categorias categoria;

    @Column(name="produto")
    private String produto;
    
    @Column(name="preco")
    private BigDecimal preco;
    
    @Column(name="quantidade")
    private Integer quantidade;
    
    @Column(name="descricao")
    private String descricao;
    
    @Column(name="foto")
    private String foto;
  
	
    public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Categorias getCategoria() {
		return categoria;
	}

	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
    public String toString() {
        return "Produtos{" +
                "idProduto=" + idProduto +
                ", categoria='" + categoria + '\'' +
                '}';
    }

	public Produtos(Long idProduto, Categorias categoria, String produto, BigDecimal preco, Integer quantidade,
			String descricao, String foto) {
		this.idProduto = idProduto;
		this.categoria = categoria;
		this.produto = produto;
		this.preco = preco;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.foto = foto;
	}
	public Produtos(Categorias categoria, String produto, BigDecimal preco, Integer quantidade,
			String descricao, String foto) {
		this.categoria = categoria;
		this.produto = produto;
		this.preco = preco;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.foto = foto;
	}

	public Produtos() {
	}
}
