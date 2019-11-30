package com.ssbrasilprev.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import com.ssbrasilprev.domain.Categorias;
import com.ssbrasilprev.domain.Produtos;
import com.ssbrasilprev.repository.CategoriasRepository;
import com.ssbrasilprev.repository.ProdutosRepository;

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
public class ProdutosControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    //@WithMockUser(username = "USER")
    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/produtos/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduto", is(1)))
                .andExpect(jsonPath("$.produto", is("produto 1")))
                .andExpect(jsonPath("$.quantidade", is(2)));
    }

    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/produtos/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
