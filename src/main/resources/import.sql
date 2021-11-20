DROP TABLE catalog;
CREATE TABLE catalog (id bigserial, tittle VARCHAR(255), cost integer,  PRIMARY KEY (id));
INSERT INTO catalog (tittle, cost) VALUES ('Milk', 100), ('Apple', 40), ('Banana', 30);
