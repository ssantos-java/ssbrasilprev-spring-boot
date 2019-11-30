package com.ssbrasilprev.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import com.ssbrasilprev.domain.PedidoItens;
import com.ssbrasilprev.domain.Pedidos;
import com.ssbrasilprev.domain.Produtos;
import com.ssbrasilprev.repository.PedidoItensRepository;
import com.ssbrasilprev.repository.PedidosRepository;
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
public class PedidoItensControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    //@WithMockUser(username = "USER")
    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/pedidoitens/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem", is(1)))
                .andExpect(jsonPath("$.quantidade", is(2)));
                //.andExpect(jsonPath("$.id_pedido", is(1)));
             
    }

    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/pedidoitens/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
