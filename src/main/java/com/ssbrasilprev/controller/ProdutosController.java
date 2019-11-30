package com.ssbrasilprev.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.ssbrasilprev.domain.Produtos;
import com.ssbrasilprev.error.ProdutoNotFoundException;
import com.ssbrasilprev.error.ProdutoUnSupportedFieldPatchException;
import com.ssbrasilprev.repository.ProdutosRepository;

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
@Api(value="Gerenciamento de Produtos", description="Operações pertencentes ao gerenciamento dos Produtos")
public class ProdutosController {

    @Autowired
    private ProdutosRepository repository;

    // Find
    @GetMapping("/produtos")
    List<Produtos> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    Produtos newProdutos(@Valid @RequestBody Produtos newProdutos) {
        return repository.save(newProdutos);
    }

    // Find
    @GetMapping("/produtos/{id}")
    Produtos findOne(@PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    // Save or update
    @PutMapping("/produtos/{id}")
    Produtos saveOrUpdate(@RequestBody Produtos newProdutos, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setCategoria(newProdutos.getCategoria());
                    x.setProduto(newProdutos.getProduto());
                    x.setPreco(newProdutos.getPreco());
                    x.setQuantidade(newProdutos.getQuantidade());
                    x.setDescricao(newProdutos.getDescricao());
                    x.setFoto(newProdutos.getFoto());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newProdutos.setIdProduto(id);
                    return repository.save(newProdutos);
                });
    }

    // update  somente campo produto
    @PatchMapping("/produtos/{id}")
    Produtos patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String produto = update.get("produto");
                    if (!StringUtils.isEmpty(produto)) {
                        x.setProduto(produto);
                        return repository.save(x);
                    } else {
                        throw new ProdutoUnSupportedFieldPatchException(update.keySet());
                    }
                })
                .orElseGet(() -> {
                    throw new ProdutoNotFoundException(id);
                });
    }

    @DeleteMapping("/produtos/{id}")
    void deleteProdutos(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
