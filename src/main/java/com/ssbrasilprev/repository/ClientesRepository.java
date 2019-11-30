package com.ssbrasilprev.repository;

import com.ssbrasilprev.domain.Clientes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {
}
