package com.ssbrasilprev.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="clientes")
@ApiModel(description = "Todos os detalhes sobre o Cliente.")
public class Clientes {

    @Id
    @GeneratedValue
    @Column(name="idCliente")
    @ApiModelProperty(notes = "O ID do Cliente gerado pelo banco de dados")
    private Long idCliente;

    @Column(name="nome", nullable = false)
    @ApiModelProperty(notes = "nome do Cliente")
    private String nome;

    @Column(name="email", nullable = false)
    @ApiModelProperty(notes = "email do Cliente")
    private String email;
    
    @Column(name="senha", nullable = false)
    @ApiModelProperty(notes = "senha do Cliente")
    private String senha;

    @Column(name="rua", nullable = false)
    private String rua;

    @Column(name="cidade", nullable = false)
    @ApiModelProperty(notes = "cidade do Cliente")
    private String cidade;
    
    @Column(name="bairro")
    @ApiModelProperty(notes = "bairro do Cliente")
    private String bairro;

    @Column(name="cep")
    @ApiModelProperty(notes = "cep do Cliente")
    private Integer cep;

    @Column(name="estado")
    @ApiModelProperty(notes = "UF do Cliente")
    private String estado;

    
    public Clientes(Long idCliente, String nome, String email, String senha, String rua, String cidade, String bairro,
        Integer cep, String estado) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.rua = rua;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.estado = estado;
    }
    public Clientes(String nome, String email, String senha, String rua, String cidade, String bairro,
        Integer cep, String estado) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.rua = rua;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.estado = estado;
    }
    
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
       
	@Override
    public String toString() {
        return "Clientes{" +
                "id=" + idCliente +
                ", name='" + nome + '\'' +
                '}';
    }

    public Clientes() {
    }
    
}
