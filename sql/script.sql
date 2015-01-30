create database cursojsf;

create table pessoa (
    codigo int not null auto_increment primary key,
    nome varchar(100) not null
);

create table lancamento (
    codigo int not null auto_increment primary key,
    tipo varchar(20) not null,
    codigo_pessoa int not null,
    descricao varchar(100) not null,
    valor decimal(10,2) not null,
    data_vencimento date not null,
    pago bit not null,
    data_pagamento date,
    foreign key (codigo_pessoa) references pessoa (codigo)
);

insert into pessoa (nome) values ('João das Chaves');
insert into pessoa (nome) values ('Thau Telecom');
insert into pessoa (nome) values ('Pastelaria 99');
insert into pessoa (nome) values ('Pastelaria do Jose');
insert into pessoa (nome) values ('Mercado Preço Bom');
insert into pessoa (nome) values ('Mercearia Vem K');