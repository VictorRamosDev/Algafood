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

set foreign_key_checks = 1;

insert ignore into cozinha (id, nome) values (1, 'Tailandesa');
insert ignore into cozinha (id, nome) values (2, 'Indiana');

insert ignore into estado (id, nome) values (1, 'Minas Gerais');
insert ignore into estado (id, nome) values (2, 'São Paulo');
insert ignore into estado (id, nome) values (3, 'Ceará');

insert ignore into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert ignore into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert ignore into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert ignore into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert ignore into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_atualizacao) values (1, 'Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', utc_timestamp, utc_timestamp);
insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (2, 'Thai Delivery', 9.5, 1, utc_timestamp, utc_timestamp);
insert ignore into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);

insert ignore into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert ignore into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert ignore into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert ignore into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert ignore into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert ignore into restaurante_formas_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,3), (3,2), (3,3);

insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Salmão', 'Salmão Frito.', 42.90, 1, 1);
insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Pastel Frito', 'Pastel frito no óleo.', 12.90, 1, 2);
insert ignore into produto(id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Banana Frita', 'Banana frita ao estilo cartola.', 18.99, 1, 3);

insert ignore into grupo(id, nome) values (1, 'Resenha da Comida');
insert ignore into grupo(id, nome) values (2, 'Grupo da pelada da quarta-feira');

insert ignore into usuario(id, nome, email, senha, data_cadastro) values (1, 'Carlos Antônio da Silva', 'carlin@teste.teste', '123Mudar', utc_timestamp);
insert ignore into usuario(id, nome, email, senha, data_cadastro) values (2, 'Thayná Richely de Brito Rangel', 'thayrichely@gmail.com', '123Mudar', utc_timestamp);
insert ignore into usuario(id, nome, email, senha, data_cadastro) values (3, 'Victor Hugo de Souza Ramos', 'victorhugopb8@gmail.com', '123Mudar', utc_timestamp);

insert ignore into grupos_permissoes(grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1);

insert ignore into usuarios_grupos(usuario_id, grupo_id) values (2, 1), (3, 1), (1, 2), (3, 2);