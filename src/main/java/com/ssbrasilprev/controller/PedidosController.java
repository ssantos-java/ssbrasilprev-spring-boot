package com.ssbrasilprev.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.ssbrasilprev.domain.Clientes;
import com.ssbrasilprev.domain.Pedidos;
import com.ssbrasilprev.error.ClienteNotFoundException;
import com.ssbrasilprev.error.PedidoNotFoundException;
import com.ssbrasilprev.error.PedidoUnSupportedFieldPatchException;
import com.ssbrasilprev.repository.ClientesRepository;
import com.ssbrasilprev.repository.PedidosRepository;

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
@Api(value="Gerenciamento de Pedidos", description="Operações pertencentes ao gerenciamento dos Pedidos")
public class PedidosController {

    @Autowired
    private PedidosRepository repository;

    @Autowired
    private ClientesRepository repositoryClientes;

    // Find
    @GetMapping("/pedidos")
    List<Pedidos> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    Pedidos newPedidos(@Valid @RequestBody Pedidos newPedidos) {
        return repository.save(newPedidos);
    }

    // Find
    @GetMapping("/pedidos/{id}")
    Pedidos findOne(@PathVariable @Min(1) Long id) {
        return repository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
    }

    // Save or update
    @PutMapping("/pedidos/{id}")
    Pedidos saveOrUpdate(@RequestBody Pedidos newPedidos, @PathVariable Long id) {

        return repository.findById(id).map(x -> {
            x.setCliente(newPedidos.getCliente());
            x.setData(newPedidos.getData());
            x.setStatus(newPedidos.getStatus());
            x.setSessao(newPedidos.getSessao());
            return repository.save(x);
        }).orElseGet(() -> {
            newPedidos.setIdPedido(id);
            return repository.save(newPedidos);
        });
    }

    // update somente Data - 
    // é melhor criar um método personalizado para atualizar um valor =: newValue em que id =: id
    @PatchMapping("/pedidos/{id}")
    Pedidos patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository.findById(id).map(x -> {

            String idCliente = update.get("idCliente");
            if (!StringUtils.isEmpty(idCliente)){
                Optional<Clientes> cliente = repositoryClientes.findById(Long.decode(idCliente));
                if (cliente.isPresent()) {
                    x.setCliente(cliente.get());
                    return repository.save(x);
                } else{
                    throw new PedidoUnSupportedFieldPatchException(update.keySet());
                }
            } else {
                throw new PedidoUnSupportedFieldPatchException(update.keySet());
            }
        })
        .orElseGet(() -> {
                            throw new ClienteNotFoundException(id);
                        });
    }

    @DeleteMapping("/pedidos/{id}")
    void deletePedidos(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
