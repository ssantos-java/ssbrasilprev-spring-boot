package com.ssbrasilprev.controller;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.ssbrasilprev.domain.Categorias;
import com.ssbrasilprev.repository.CategoriasRepository;

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
public class CategoriasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriasRepository mockRepository;

    @Before
    public void init() {
        Categorias categorias = new Categorias(1L, "categoria 1");
        
        when(mockRepository.findById(1L)).thenReturn(Optional.of(categorias));
    }

    //@WithMockUser(username = "USER")
    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/categorias/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria", is(1)))
                .andExpect(jsonPath("$.categoria", is("categoria 1")));
    }

    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/categorias/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
