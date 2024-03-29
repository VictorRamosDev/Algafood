create table forma_pagamento (
    id bigint not null auto_increment,
    descricao varchar(255) not null,

    primary key (id)
) engine=InnoDB charset=utf8;

create table grupo (
    id bigint not null auto_increment,
    nome varchar(255),
    primary key (id)
) engine=InnoDB charset=utf8;

create table grupos_permissoes (
    grupo_id bigint not null,
    permissao_id bigint not null
) engine=InnoDB charset=utf8;

create table permissao (
    id bigint not null auto_increment,
    descricao varchar(255) not null,
    nome varchar(255) not null,

    primary key (id)
) engine=InnoDB charset=utf8;

create table produto (
    id bigint not null auto_increment,
    ativo bit not null,
    descricao varchar(255),
    nome varchar(255),
    preco decimal(19,2),
    restaurante_id bigint,

    primary key (id)
) engine=InnoDB charset=utf8;

create table restaurante (
    id bigint not null auto_increment,
    data_atualizacao datetime not null,
    data_cadastro datetime not null,
    endereco_bairro varchar(255),
    endereco_cep varchar(255),
    endereco_complemento varchar(255),
    endereco_logradouro varchar(255),
    endereco_numero varchar(255),
    nome varchar(255) not null,
    taxa_frete decimal(19,2) not null,
    cozinha_id bigint not null,
    endereco_cidade_id bigint,

    primary key (id)
) engine=InnoDB charset=utf8;

create table restaurante_formas_pagamento (
    restaurante_id bigint not null,
    forma_pagamento_id bigint not null
) engine=InnoDB charset=utf8;

create table usuario (
    id bigint not null auto_increment,
    data_cadastro datetime not null,
    email varchar(255),
    nome varchar(255),
    senha varchar(255),

    primary key (id)
) engine=InnoDB charset=utf8;

create table usuarios_grupos (
    usuario_id bigint not null,
    grupo_id bigint not null
) engine=InnoDB charset=utf8;

alter table grupos_permissoes
    add constraint fk_grupo_permissoes_permissao foreign key (permissao_id)
    references permissao (id);

alter table grupos_permissoes
    add constraint fk_grupo_permissoes_grupo foreign key (grupo_id)
    references grupo (id);

alter table produto
    add constraint fk_produto_restaurante foreign key (restaurante_id)
    references restaurante (id);

alter table restaurante
    add constraint fk_restaurante_cozinha foreign key (cozinha_id)
    references cozinha (id);

alter table restaurante
    add constraint fk_restaurante_cidade foreign key (endereco_cidade_id)
    references cidade (id);

alter table restaurante_formas_pagamento
    add constraint fk_restaurante_formas_pagamento_forma_pagamento foreign key (forma_pagamento_id)
    references forma_pagamento (id);

alter table restaurante_formas_pagamento
    add constraint fk_restaurante_formas_pagamento_restaurante foreign key (restaurante_id)
    references restaurante (id);

alter table usuarios_grupos
    add constraint fk_usuarios_grupos_grupo foreign key (grupo_id)
    references grupo (id);

alter table usuarios_grupos
    add constraint fk_usuarios_grupos_usuario foreign key (usuario_id)
    references usuario (id);