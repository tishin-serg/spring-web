create table if not exists catalog (id bigserial primary key, tittle varchar(255), cost int);

insert into catalog (tittle, cost)
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