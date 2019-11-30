package com.ssbrasilprev.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssbrasilprev.domain.Clientes;
import com.ssbrasilprev.domain.Pedidos;
import com.ssbrasilprev.repository.ClientesRepository;
import com.ssbrasilprev.repository.PedidosRepository;

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
public class PedidosControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    //@WithMockUser is not working with TestRestTemplate
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PedidosRepository mockRepository;

    @MockBean
    private ClientesRepository mockRepositoryCliente;

    @Before
    public void init() {

        Clientes cliente = mockRepositoryCliente.getOne(1L);
        Pedidos pedidos = new Pedidos(1L, cliente, Calendar.getInstance().getTime(), "ativo", "10");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(pedidos));
    }
    
    @Test
    public void find_login_ok() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/pedidos/1", String.class);

        printJSON(response);
        
        Pedidos pedidosResp = objectMapper.readValue(response.getBody(), Pedidos.class); 

        System.out.println("response.getBody() = "+ response.getBody());

        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());
            
        assertEquals(1L,pedidosResp.getIdPedido().longValue());
        assertEquals("ativo",pedidosResp.getStatus());
    }

    @Test
    public void find_nologin_401() throws Exception {

        String expected = "{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Unauthorized\",\"path\":\"/pedidos/1\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/pedidos/1", String.class);

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
