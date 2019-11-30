package com.ssbrasilprev.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.ssbrasilprev.domain.PedidoItens;
import com.ssbrasilprev.error.PedidoItensNotFoundException;
import com.ssbrasilprev.error.PedidoItensUnSupportedFieldPatchException;
import com.ssbrasilprev.repository.PedidoItensRepository;

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
@Api(value="Gerenciamento de Itens dos Pedido", description="Operações pertencentes ao gerenciamento dos Itens dos Pedidos")
public class PedidoItensController {

    @Autowired
    private PedidoItensRepository repository;

    // Find
    @GetMapping("/pedidoitens")
    List<PedidoItens> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/pedidoitens")
    @ResponseStatus(HttpStatus.CREATED)
    PedidoItens newPedidoItens(@Valid @RequestBody PedidoItens newPedidoItens) {
        return repository.save(newPedidoItens);
    }

    // Find
    @GetMapping("/pedidoitens/{id}")
    PedidoItens findOne(@PathVariable @Min(1) Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PedidoItensNotFoundException(id));
    }

    // Save or update
    @PutMapping("/pedidoitens/{id}")
    PedidoItens saveOrUpdate(@RequestBody PedidoItens newPedidoItens, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setPedido(newPedidoItens.getPedido());
                    x.setProduto(newPedidoItens.getProduto());
                    x.setQuantidade(newPedidoItens.getQuantidade());
                    x.setValor(newPedidoItens.getValor());
                    x.setSubtotal(newPedidoItens.getSubtotal());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newPedidoItens.setIdItem(id);
                    return repository.save(newPedidoItens);
                });
    }

    // update  somente quantidade
    @PatchMapping("/pedidoitens/{id}")
    PedidoItens patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String quantidade = update.get("quantidade");
                    if (!StringUtils.isEmpty(quantidade)) {
                        x.setQuantidade(Integer.parseInt(quantidade));
                        return repository.save(x);
                    } else {
                        throw new PedidoItensUnSupportedFieldPatchException(update.keySet());
                    }
                })
                .orElseGet(() -> {
                    throw new PedidoItensNotFoundException(id);
                });
    }

    @DeleteMapping("/pedidoitens/{id}")
    void deletePedidoItens(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
