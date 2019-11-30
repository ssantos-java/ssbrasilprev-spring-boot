package com.ssbrasilprev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.ssbrasilprev.domain.Categorias;
import com.ssbrasilprev.error.CategoriaNotFoundException;
import com.ssbrasilprev.error.CategoriaUnSupportedFieldPatchException;
import com.ssbrasilprev.error.ClienteNotFoundException;
import com.ssbrasilprev.repository.CategoriasRepository;

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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
@Api(value="Gerenciamento de Categorias", description="Operações pertencentes ao gerenciamento das Categorias")
public class CategoriasController {

    @Autowired
    private CategoriasRepository repository;

    // Find
    @ApiOperation (value = "Visualizar a lista de categorias disponíveis", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Sucesso na leitura da lista"),
        @ApiResponse(code = 401, message = "Você não tem autorização para acessar o recurso"),
        @ApiResponse(code = 403, message = "É proibido acessar o recurso que você estava tentando acessar"),
        @ApiResponse(code = 404, message = "O recurso que você estava tentando acessar não foi encontrado")
    })
    @GetMapping("/categorias")
    List<Categorias> findAll() {
        return repository.findAll();
    }

    // Save
    @ApiOperation(value = "Salvar uma categoria")
    @PostMapping("/categorias")
    @ResponseStatus(HttpStatus.CREATED)
    Categorias newCategorias(
        @ApiParam(value = "Grava uma nova Categoria na tabela do banco de dados", required = true) 
        @Valid @RequestBody Categorias newCategorias) {
        
            return repository.save(newCategorias);
    }

    // Find
    @ApiOperation(value = "Obter uma Categoria pelo seu ID")
    @GetMapping("/categorias/{id}")
    Categorias findOne(
        @ApiParam(value = "ID da Categoria do qual o objeto Categorias será recuperado", required = true) 
        @PathVariable @Min(1) Long id) {
        
            return repository.findById(id)
                    .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    // Save or update
    @ApiOperation(value = "Salvar ou Atualizar uma categoria")
    @PutMapping("/categorias/{id}")
    Categorias saveOrUpdate(
        @ApiParam(value = "O objeto Categoria para atualizar", required = true) @Valid @RequestBody Categorias newCategorias,
        @ApiParam(value = "Id da Categoria para atualizar", required = true) @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setCategoria(newCategorias.getCategoria());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newCategorias.setIdCategoria(id);
                    return repository.save(newCategorias);
                });
    }

    // update somente nome
    @ApiOperation(value = "Atualizar um campo da Categoria")
    @PatchMapping("/categorias/{id}")
    Categorias patch(
        @ApiParam(value = "Campos da Categoria a atualizar", required = true) @Valid @RequestBody Map<String, String> update, 
        @ApiParam(value = "ID da Categoria que será atualizada tabela no banco de dados", required = true) @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String categoria = update.get("categoria");
                    if (!StringUtils.isEmpty(categoria)) {
                        x.setCategoria(categoria);
                        return repository.save(x);
                    } else {
                        throw new CategoriaUnSupportedFieldPatchException(update.keySet());
                    }
                })
                .orElseGet(() -> {
                    throw new CategoriaNotFoundException(id);
                });
    }

    @ApiOperation(value = "Deleta uma categoria")
    @DeleteMapping("/categorias/{id}")
    Map < String, Boolean > deleteCategorias(
        @ApiParam(value = "ID da Categoria do qual será excluído da tabela do banco de dados", required = true) @PathVariable Long id) 
        throws CategoriaNotFoundException {
      
        Categorias categoria = repository.findById(id)
            .orElseThrow(() -> new CategoriaNotFoundException(id));

        repository.delete(categoria);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deletado", Boolean.TRUE);

        return response;
    }
}
