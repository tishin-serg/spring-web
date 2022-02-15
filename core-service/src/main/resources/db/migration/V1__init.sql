create table if not exists categories (id bigserial primary key, tittle varchar(255));

create table if not exists products (
                                     id             bigserial primary key,
                                     category_id    bigint references categories (id),
                                     tittle         varchar(255), cost int
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
('Milk', 3, 80),
('Apple', 1, 100),
('Banana', 1, 120),
('Beef', 5, 300),
('Cherry', 1, 50),
('Tomato', 2, 180),
('Cucumber', 2, 100),
('Bread', 5, 60),
('Lemon', 1, 220),
('Orange', 1, 130),
('Potato', 2, 40),
('Cheese', 5, 115),
('Chicken', 5, 250),
('Water', 3, 30),
('Melon', 1, 180),
('Beer', 3, 80),
('Fish', 5, 350),
('Ice cream', 4, 50),
('Chocolate', 4, 60),
('Juice', 3, 90);

create table orders (
    id          bigserial primary key,
    username     varchar(255) not null,
    total_price int not null,
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
    price_per_product   int,
    price               int,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values
('bob', 1040, 'Moscow', '555-555'),
('john', 1320, 'Moscow', '555-556'),
('bob', 2540, 'Moscow', '555-555'),
('john', 990, 'Moscow', '555-556');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values
(1, 1, 3, 80, 240),
(2, 1, 2, 100, 200),
(3, 2, 6, 120, 720),
(4, 1, 2, 300, 600),
(4, 2, 1, 300, 300),
(4, 3, 5, 300, 1500),
(5, 3, 10, 50, 500),
(6, 3, 3, 180, 540),
(7, 4, 3, 100, 300),
(7, 2, 3, 100, 300),
(8, 4, 3, 220, 660);

insert into order_items (product_id, order_id, quantity, price_per_product, price, created_at)
values
(1, 4, 300, 80, 24000, '2022-01-01 01:00:00');
