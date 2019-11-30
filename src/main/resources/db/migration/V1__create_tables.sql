CREATE TABLE clientes (
        id_cliente bigint not null,
        bairro varchar(255),
        cep integer,
        cidade varchar(255) not null,
        email varchar(255) not null,
        estado varchar(255),
        nome varchar(255) not null,
        rua varchar(255) not null,
        senha varchar(255) not null,
        primary key (id_cliente)
);
    create table categorias (
        id_categoria bigint not null,
        categoria varchar(255),
        primary key (id_categoria)
    );

    create table pedidoitens (
        id_item bigint not null,
        id_pedido bigint not null,
        id_produto bigint not null,
        quantidade integer,
        subtotal decimal(19,2),
        valor decimal(19,2),
        primary key (id_item)
    );

    create table pedidos (
        id_pedido bigint not null,
        id_cliente bigint not null,
        data date,
        sessao varchar(255),
        status varchar(255),
        primary key (id_pedido)
    );

    create table produtos (
        id_produto bigint not null,
        id_categoria bigint not null,
        produto varchar(255),
        preco decimal(19,2),
        quantidade integer,
        descricao varchar(255),
        foto varchar(255),
        primary key (id_produto)
    );
 create sequence hibernate_sequence start with 1 increment by 1;

    alter table pedidoitens
       add constraint FKfdso2gxupu7ni53ocxyoeyuoj
       foreign key (id_pedido)
       references pedidos;

    alter table pedidoitens
       add constraint FKjvq73e2sg3p68o6qld6pqx4rs
       foreign key (id_produto)
       references produtos;

    alter table pedidos
       add constraint FKdnomiluem4t3x66t6b9aher47
       foreign key (id_cliente)
       references clientes;

    alter table produtos
       add constraint FKa8foyho8afg14197ix9qndh2
       foreign key (id_categoria)
       references categorias;