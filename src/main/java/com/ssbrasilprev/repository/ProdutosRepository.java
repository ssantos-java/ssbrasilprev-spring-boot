package com.ssbrasilprev.repository;

import com.ssbrasilprev.domain.Produtos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
}
