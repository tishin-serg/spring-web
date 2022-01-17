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

create table users (
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    created_at     timestamp default current_timestamp,
    updated_at     timestamp default current_timestamp,
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

create table orders (
    id          bigserial primary key,
    user_id     bigint not null references users (id),
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