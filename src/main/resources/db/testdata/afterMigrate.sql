set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from restaurante_formas_pagamento;
delete from forma_pagamento;
delete from grupos_permissoes;
delete from usuarios_grupos;
delete from grupo;
delete from permissao;
delete from produto;
delete from restaurante;
delete from usuario;
delete from restaurantes_responsaveis;
delete from item_pedido;
delete from pedido;


set foreign_key_checks = 1;
SET @count = 1;

UPDATE cidade SET cidade.id = @count;
UPDATE cozinha SET cozinha.id = @count;
UPDATE estado SET estado.id = @count;
UPDATE forma_pagamento SET forma_pagamento.id = @count;
UPDATE grupo SET grupo.id = @count;
UPDATE permissao SET permissao.id = @count;
UPDATE produto SET produto.id = @count;
UPDATE restaurante SET restaurante.id = @count;
UPDATE usuario SET usuario.id = @count;
UPDATE pedido SET pedido.id = @count;
UPDATE item_pedido SET item_pedido.id = @count;

ALTER TABLE cidade auto_increment = 1;
ALTER TABLE cozinha auto_increment = 1;
ALTER TABLE estado auto_increment = 1;
ALTER TABLE forma_pagamento auto_increment = 1;
ALTER TABLE grupo auto_increment = 1;
ALTER TABLE permissao auto_increment = 1;
ALTER TABLE produto auto_increment = 1;
ALTER TABLE restaurante auto_increment = 1;
ALTER TABLE usuario auto_increment = 1;
ALTER TABLE pedido auto_increment = 1;
ALTER TABLE item_pedido auto_increment = 1;

insert ignore into cozinha (id, nome) values (1, 'Tailandesa');
insert ignore into cozinha (id, nome) values (2, 'Indiana');
insert ignore into cozinha (id, nome) values (3, 'Brasileira');

insert ignore into estado (id, nome) values (1, 'Minas Gerais');
insert ignore into estado (id, nome) values (2, 'São Paulo');
insert ignore into estado (id, nome) values (3, 'Ceará');

insert ignore into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert ignore into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert ignore into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert ignore into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert ignore into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Thai Delivery', 9.5, 1, utc_timestamp, utc_timestamp, true, true);
insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Bar do Cuscuz', 10, 3, utc_timestamp, utc_timestamp, true, true);

insert ignore into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert ignore into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert ignore into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert ignore into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert ignore into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert ignore into restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3), (4,1), (4,2);

insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Salmão', 'Salmão Frito.', 42.90, 1, 1);
insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Pastel Frito', 'Pastel frito no óleo.', 12.90, 1, 2);
insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Banana Frita', 'Banana frita ao estilo cartola.', 18.99, 1, 3);
insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (4, 'Cuscuz Original', 'Cuscuz regional autêntico.', 10.99, 1, 4);

insert ignore into usuario(id, nome, email, senha, data_cadastro) values (1, 'Carlos Antônio da Silva', 'carlin@teste.teste', '123Mudar', utc_timestamp);
insert ignore into usuario(id, nome, email, senha, data_cadastro) values (2, 'Thayná Richely de Brito Rangel', 'thayrichely@gmail.com', '123Mudar', utc_timestamp);
insert ignore into usuario(id, nome, email, senha, data_cadastro) values (3, 'Victor Hugo de Souza Ramos', 'victorhugopb8@gmail.com', '123Mudar', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro)
    values (4, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
           (5, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
           (6, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
           (7, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp);

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into grupos_permissoes (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert ignore into usuarios_grupos(usuario_id, grupo_id) values (2, 1), (3, 1), (1, 2), (3, 2);

insert ignore into restaurantes_responsaveis(restaurante_id, usuario_id) values (1, 1), (1, 2), (2, 3), (3, 5);

insert into pedido (id, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, sub_total, taxa_frete, valor_total)
    values (1, 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
    'CRIADO', utc_timestamp, 68.7, 10, 78.7);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
    values (1, 1, 1, 1, 42.9, 42.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
    values (2, 1, 2, 2, 12.9, 25.8, 'Menos picante, por favor');

insert into pedido (id, restaurante_id, cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, sub_total, taxa_frete, valor_total)
    values (2, 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
    'CRIADO', utc_timestamp, 18.99, 10, 28.99);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
    values (3, 2, 3, 1, 18.99, 18.99, 'Sem cebola');
