package com.ssbrasilprev.controller;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Optional;

import com.ssbrasilprev.domain.Clientes;
import com.ssbrasilprev.domain.Pedidos;
import com.ssbrasilprev.repository.ClientesRepository;
import com.ssbrasilprev.repository.PedidosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    //@WithMockUser(username = "USER")
    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/pedidos/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido", is(1)))
                .andExpect(jsonPath("$.status", is("ativo")))
                .andExpect(jsonPath("$.sessao", is("10")));
    }

    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/pedidos/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
