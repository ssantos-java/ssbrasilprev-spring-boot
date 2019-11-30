package com.ssbrasilprev.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssbrasilprev.domain.PedidoItens;
import com.ssbrasilprev.domain.Pedidos;
import com.ssbrasilprev.domain.Produtos;
import com.ssbrasilprev.repository.PedidoItensRepository;
import com.ssbrasilprev.repository.PedidosRepository;
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
public class PedidoItensControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    //@WithMockUser is not working with TestRestTemplate
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PedidoItensRepository mockRepository;

    @MockBean
    private PedidosRepository mockRepositoryPedido;

    @MockBean
    private ProdutosRepository mockRepositoryProduto;

    @Before
    public void init() {

        Pedidos pedido = mockRepositoryPedido.getOne(1L);
        Produtos produto = mockRepositoryProduto.getOne(1L);

        PedidoItens PedidoItens = new PedidoItens(1L, produto, pedido, 2, new BigDecimal(2.2), new BigDecimal(20.2));
  
        when(mockRepository.findById(1L)).thenReturn(Optional.of(PedidoItens));

    }
    
    @Test
    public void find_login_ok() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/pedidoitens/1", String.class);

        printJSON(response);
        
        PedidoItens PedidoItensResp = objectMapper.readValue(response.getBody(), PedidoItens.class); 

        System.out.println("pedidoItens, response.getBody() = "+ response.getBody());

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
            
        assertEquals(1L,PedidoItensResp.getIdItem().longValue());
        assertEquals(2,PedidoItensResp.getQuantidade().intValue());
    }

    @Test
    public void find_nologin_401() throws Exception {

        String expected = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Unauthorized\",\"path\":\"/pedidoitens/1\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/pedidoitens/1", String.class);

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
