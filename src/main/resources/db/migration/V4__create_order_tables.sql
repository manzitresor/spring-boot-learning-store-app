create table orders
(
    id          bigint auto_increment
        primary key,
    customer_id bigint                        not null,
    status      varchar(20)                   not null,
    created_at datetime not null default current_timestamp,
    total_price decimal(10, 2)                not null,
    constraint orders_users_id_fk
        foreign key (customer_id) references users (id)
);

create table order_items
(
    id          int auto_increment
        primary key,
    product_id  bigint         not null,
    unit_price  decimal(10, 2) not null,
    quantity    int            not null,
    total_price decimal(10, 2) not null,
    order_id    bigint            not null,
    constraint order_items_orders_id_fk
        foreign key (order_id) references orders (id),
    constraint order_items_products_id_fk
        foreign key (product_id) references products (id)
);