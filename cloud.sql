create table element
(
    name        varchar(255) not null
        primary key,
    price       numeric      not null,
    value       integer      not null,
    description text,
    image       text
);

alter table element
    owner to postgres;

create table userinfo
(
    userid     serial
        primary key,
    username   varchar(255) not null
        unique,
    password   varchar(255) not null,
    permission varchar(50) default 'user'::character varying,
    value      numeric     default 0
);

alter table userinfo
    owner to postgres;

create table orders
(
    id       bigserial
        primary key,
    order_id varchar(255) not null,
    other    text,
    username varchar(255),
    value    numeric      not null,
    time     timestamp default CURRENT_TIMESTAMP
);

alter table orders
    owner to postgres;

create table token
(
    token   varchar(255) not null
        primary key,
    user_id integer
        references userinfo,
    time    timestamp default CURRENT_TIMESTAMP
);

alter table token
    owner to postgres;

