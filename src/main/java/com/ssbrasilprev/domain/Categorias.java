package com.ssbrasilprev.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="categorias")
@ApiModel(description = "Todos os detalhes sobre a Categoria.")
public class Categorias {

    @Id
    @GeneratedValue
    @Column(name="idCategoria")
    @ApiModelProperty(notes = "O ID da Categoria gerado pelo banco de dados")
    private Long idCategoria;

    @Column(name="categoria")
    @ApiModelProperty(notes = "A descrição da Categoria")
    private String categoria;
    
    
    public Categorias() { }

    public Categorias(Long id, String categoria) {
        this.idCategoria = id;
        this.categoria = categoria;
    }

    public Categorias(String categoria) {
        this.categoria = categoria;
    }

    public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
    public String toString() {
        return "Categorias [categoria=" + categoria + ", idCategoria=" + idCategoria + "]";
    }
}
