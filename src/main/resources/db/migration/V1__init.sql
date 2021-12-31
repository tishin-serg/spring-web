create table if not exists products (id bigserial primary key, tittle varchar(255), cost int);

insert into products (tittle, cost)
values
('Milk', 80),
('Apple', 100),
('Banana', 120),
('Beef', 300),
('Cherry', 50),
('Tomato', 180),
('Cucumber', 100),
('Bread', 60),
('Lemon', 220),
('Orange', 130),
('Potato', 40),
('Cheese', 115),
('Chicken', 250),
('Water', 30),
('Melon', 180),
('Beer', 80),
('Fish', 350),
('Ice cream', 50),
('Chocolate', 60),
('Juice', 90);

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
    phone       varchar(255)
);

create table order_items (
    id                  bigserial primary key,
    product_id          bigint references products (id),
    user_id             bigint references users (id),
    order_id            bigint references orders (id),
    quantity            int,
    price_per_product   int,
    price               int
);