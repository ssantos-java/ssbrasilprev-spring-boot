package com.ssbrasilprev.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssbrasilprev.domain.Categorias;
import com.ssbrasilprev.domain.Produtos;
import com.ssbrasilprev.repository.CategoriasRepository;
import com.ssbrasilprev.repository.ProdutosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProdutosControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    //@WithMockUser is not working with TestRestTemplate
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProdutosRepository mockRepository;

    @MockBean
    private CategoriasRepository mockRepositoryCategoria;

    @Before
    public void init() {

        Categorias categoria = mockRepositoryCategoria.getOne(1L);

        Produtos Produtos = new Produtos(1L, categoria, "produto 1", new BigDecimal(10.2), 2, "descricao 1","foto");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(Produtos));
    }
    
    @Test
    public void find_login_ok() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/produtos/1", String.class);

        printJSON(response);
        
        Produtos ProdutosResp = objectMapper.readValue(response.getBody(), Produtos.class); 

        System.out.println("produto, response.getBody() = "+ response.getBody());

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
            
        assertEquals(1L,ProdutosResp.getIdProduto().longValue());
        assertEquals("produto 1",ProdutosResp.getProduto());
    }

    @Test
    public void find_nologin_401() throws Exception {

        String expected = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Unauthorized\",\"path\":\"/produtos/1\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/produtos/1", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
