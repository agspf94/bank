drop table if exists bank;
create table bank (
                    id integer generated by default as identity,
                    account_number integer,
                    owner varchar(255),
                    balance double not null,
                    document varchar(255),
                    primary key (id)
);

drop table if exists user;
create table user (
                    id integer generated by default as identity,
                    login varchar(255),
                    password varchar(255),
                    name varchar(255),
                    email varchar(255),
                    primary key (id)
);

drop table if exists role;
create table role (
                    id integer generated by default as identity,
                    name varchar(255),
                    primary key (id)
);

drop table if exists user_roles;
create table user_roles (
                            user_id integer not null,
                            role_id integer not null
);

INSERT INTO bank VALUES (1,1,'Anderson',100,'1001'),
                        (2,2,'Giuseppe',100,'1002'),
                        (3,3,'Saraiva',100,'1003'),
                        (4,4,'Patriarca',100,'1004');

INSERT INTO role VALUES (1,'ROLE_ADMIN'),
                        (2,'ROLE_USER');

INSERT INTO user_roles VALUES (1,1),
                              (2,2);

INSERT INTO user VALUES (1, 'admin', '$2a$10$UF8DYTx9RTqKfhz.lo0wCeFB3n.LYIe2XEmuD33ba87bh.9rSC0/u', 'admin', 'admin@pagseguro.com.br'),
                        (2, 'user', '$2a$10$ng6zbTeWoTvSkcvmCxgNbO1hIv/RJu2CQO20U9yx7C9.poRwJ4Efm', 'user', 'user@pagseguro.com.br');