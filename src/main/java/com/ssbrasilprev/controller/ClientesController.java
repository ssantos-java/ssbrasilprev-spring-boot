package com.ssbrasilprev.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.ssbrasilprev.domain.Clientes;
import com.ssbrasilprev.error.ClienteNotFoundException;
import com.ssbrasilprev.error.ClienteUnSupportedFieldPatchException;
import com.ssbrasilprev.repository.ClientesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Validated
@Api(value="Gerenciamento de Clientes", description="Operações pertencentes ao gerenciamento dos Clientes")
public class ClientesController {

    @Autowired
    private ClientesRepository repository;

    // Find
    @GetMapping("/clientes")
    List<Clientes> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    Clientes newClientes(@Valid @RequestBody Clientes newClientes) {
        return repository.save(newClientes);
    }

    // Find
    @GetMapping("/clientes/{id}")
    Clientes findOne(@PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    // Save or update
    @PutMapping("/clientes/{id}")
    Clientes saveOrUpdate(@RequestBody Clientes newClientes, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setNome(newClientes.getNome());
                    x.setEmail(newClientes.getEmail());
                    x.setSenha(newClientes.getSenha());
                    x.setRua(newClientes.getRua());
                    x.setCidade(newClientes.getCidade());
                    x.setBairro(newClientes.getBairro());
                    x.setCep(newClientes.getCep());
                    x.setEstado(newClientes.getEstado());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newClientes.setIdCliente(id);
                    return repository.save(newClientes);
                });
    }

    // update  somente nome
    @PatchMapping("/clientes/{id}")
    Clientes patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String nome = update.get("nome");
                    if (!StringUtils.isEmpty(nome)) {
                        x.setNome(nome);
                        // é melhor criar um método personalizado para atualizar um valor =: newValue em que id =: id
                        return repository.save(x);
                    } else {
                        throw new ClienteUnSupportedFieldPatchException(update.keySet());
                    }
                })
                .orElseGet(() -> {
                    throw new ClienteNotFoundException(id);
                });
    }

    @DeleteMapping("/clientes/{id}")
    void deleteClientes(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
