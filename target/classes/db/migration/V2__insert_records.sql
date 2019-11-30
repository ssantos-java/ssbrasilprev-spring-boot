INSERT INTO clientes (id_cliente, bairro, cep, cidade, email, estado, nome, rua, senha ) 
        VALUES (1, 'bairro', 111, 'cidade', 'email 1','estado', 'nome 1', 'rua', 'senha');

INSERT INTO clientes (id_cliente, bairro, cep, cidade, email, estado, nome, rua, senha ) 
        VALUES (2, 'bairro', 222, 'cidade', 'email 2','estado', 'nome 2', 'rua', 'senha');

INSERT INTO clientes (id_cliente, bairro, cep, cidade, email, estado, nome, rua, senha ) 
        VALUES (3, 'bairro', 333, 'cidade', 'email 3','estado', 'nome 3', 'rua', 'senha');


INSERT INTO pedidos (id_pedido, id_cliente,  data, status, sessao ) 
        VALUES (1, 1, '2019-10-10', 'ativo','10');

INSERT INTO pedidos (id_pedido, id_cliente,  data, status, sessao ) 
        VALUES (2, 2, '2019-11-10', 'entregue','20');

INSERT INTO pedidos (id_pedido, id_cliente,  data, status, sessao ) 
        VALUES (3, 3, '2019-12-10', 'pendente','30');


INSERT INTO categorias (id_categoria, categoria ) 
        VALUES (1, 'categoria 1');

INSERT INTO categorias (id_categoria, categoria ) 
        VALUES (2, 'categoria 2');

INSERT INTO categorias (id_categoria, categoria ) 
        VALUES (3, 'categoria 3');


INSERT INTO produtos (id_produto, id_categoria, produto, preco, quantidade, descricao, foto) 
        VALUES (1, 1, 'produto 1', 10.2, 2, 'descricao 1','foto');

INSERT INTO produtos (id_produto, id_categoria, produto, preco, quantidade, descricao, foto) 
        VALUES (2, 2, 'produto 2', 100.2, 4, 'descricao 2','foto');

INSERT INTO produtos (id_produto, id_categoria, produto, preco, quantidade, descricao, foto) 
        VALUES (3, 3, 'produto 3', 200.2, 4, 'descricao 3','foto');


INSERT INTO pedidoitens (id_item, id_pedido, id_produto, quantidade, valor, subtotal) 
        VALUES (1, 1, 1, 2, 2.2,  20.2);

INSERT INTO pedidoitens (id_item, id_pedido, id_produto, quantidade, valor, subtotal) 
        VALUES (2, 2, 2, 10, 10.2,  30.2);

INSERT INTO pedidoitens (id_item, id_pedido, id_produto, quantidade, valor, subtotal) 
        VALUES (3, 3, 3, 20, 20.2,  40.2);
