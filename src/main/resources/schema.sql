drop table if exists customers;
drop table if exists products;

create table if not exists customers (
    id bigserial primary key,
    firstname text not null,
    lastname text not null,
    email text unique not null,
    deleted boolean not null
);

create table if not exists products (
    id bigserial primary key,
    product_name text unique not null,
    current_inventory integer not null,
    product_price decimal(15, 2) not null
);