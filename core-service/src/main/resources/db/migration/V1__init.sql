create table if not exists categories (id bigserial primary key, tittle varchar(255));

create table if not exists products (
                                     id             bigserial primary key,
                                     category_id    bigint references categories (id),
                                     tittle         varchar(255) not null,
                                     cost           numeric(8, 2) not null
                                     );

insert into categories (tittle)
values ('Fruits'),
('Vegetables'),
('Drinks'),
('Deserts'),
('Main'),
('Other');

insert into products (tittle, category_id, cost)
values
('Milk', 3, 80.20),
('Apple', 1, 100.60),
('Banana', 1, 120.00),
('Beef', 5, 300.00),
('Cherry', 1, 50.00),
('Tomato', 2, 180.00),
('Cucumber', 2, 100.00),
('Bread', 5, 60.00),
('Lemon', 1, 220.00),
('Orange', 1, 130.00),
('Potato', 2, 40.00),
('Cheese', 5, 115.00),
('Chicken', 5, 250.00),
('Water', 3, 30.00),
('Melon', 1, 180.00),
('Beer', 3, 80.00),
('Fish', 5, 350.00),
('Ice cream', 4, 50.00),
('Chocolate', 4, 60.00),
('Juice', 3, 90.00);

create table orders (
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price numeric(8, 2) not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items (
    id                  bigserial primary key,
    product_id          bigint references products (id),
    order_id            bigint references orders (id),
    quantity            int,
    price_per_product   numeric(8, 2),
    price               numeric(8, 2),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values
('bob', 1040.00, 'Moscow', '555-555'),
('john', 1320.00, 'Moscow', '555-556'),
('bob', 2540.00, 'Moscow', '555-555'),
('john', 990.00, 'Moscow', '555-556');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values
(1, 1, 3, 80.20, 240.00),
(2, 1, 2, 100.60, 200.00),
(3, 2, 6, 120.00, 720.00),
(4, 1, 2, 300.00, 600.00),
(4, 2, 1, 300.00, 300.00),
(4, 3, 5, 300.00, 1500.00),
(5, 3, 10, 50.00, 500.00),
(6, 3, 3, 180.00, 540.00),
(7, 4, 3, 100.00, 300.00),
(7, 2, 3, 100.00, 300.00),
(8, 4, 3, 220.00, 660.00);

insert into order_items (product_id, order_id, quantity, price_per_product, price, created_at)
values
(1, 4, 300, 80.20, 24060.00, '2022-01-01 01:00:00');
