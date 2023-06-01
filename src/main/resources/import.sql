insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values ('Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Thai Delivery', 9.5, 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3);

insert into produto(nome, descricao, preco, ativo, restaurante_id) values ('Salmão', 'Salmão Frito.', 42.90, 1, 1);
insert into produto(nome, descricao, preco, ativo, restaurante_id) values ('Pastel Frito', 'Pastel frito no óleo.', 12.90, 1, 2);
insert into produto(nome, descricao, preco, ativo, restaurante_id) values ('Banana Frita', 'Banana frita ao estilo cartola.', 18.99, 1, 3);

insert into grupo(nome) values ('Resenha da Comida');
insert into grupo(nome) values ('Grupo da pelada da quarta-feira');

insert into usuario(nome, email, senha, data_cadastro) values ('Carlos Antônio da Silva', 'carlin@teste.teste', '123Mudar', utc_timestamp);
insert into usuario(nome, email, senha, data_cadastro) values ('Thayná Richely de Brito Rangel', 'thayrichely@gmail.com', '123Mudar', utc_timestamp);
insert into usuario(nome, email, senha, data_cadastro) values ('Victor Hugo de Souza Ramos', 'victorhugopb8@gmail.com', '123Mudar', utc_timestamp);

insert into grupos_permissoes(grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1);

insert into usuarios_grupos(usuario_id, grupo_id) values (2, 1), (3, 1), (1, 2), (3, 2);