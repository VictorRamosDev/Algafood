create table restaurantes_responsaveis(
    restaurante_id bigint not null,
    usuario_id bigint not null,

    primary key (restaurante_id, usuario_id)
) engine=InnoDB charset=utf8;

alter table restaurantes_responsaveis add constraint fk_restaurantes_responsaveis_restaurante
foreign key (restaurante_id) references restaurante (id);

alter table restaurantes_responsaveis add constraint fk_restaurantes_responsaveis_responsavel
foreign key (usuario_id) references usuario (id);