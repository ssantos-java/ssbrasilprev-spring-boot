package com.ssbrasilprev.controller;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.ssbrasilprev.domain.Clientes;
import com.ssbrasilprev.repository.ClientesRepository;

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
public class ClientesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientesRepository mockRepository;

    @Before
    public void init() {
        Clientes cliente = new Clientes(1L, "nome 1", "email 1","senha", "rua", "cidade", "bairro", 111, "estado");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(cliente));
    }

    //@WithMockUser(username = "USER")
    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/clientes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente", is(1)))
                .andExpect(jsonPath("$.nome", is("nome 1")))
                .andExpect(jsonPath("$.senha", is("senha")))
                .andExpect(jsonPath("$.rua", is("rua")));
    }

    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/clientes/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
