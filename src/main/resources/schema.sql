drop table if exists customers;

create table if not exists customers (
    index bigserial primary key,
    firstname text not null,
    lastname text not null,
    email text not null
);