DROP TABLE IF EXISTS categories CASCADE;
CREATE TABLE categories
(
    id SERIAL,
    name VARCHAR(15) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO categories (id, name) VALUES (1, 'catagory1');
INSERT INTO categories (id, name) VALUES (2, 'catagory2');
INSERT INTO categories (id, name) VALUES (3, 'catagory3');
INSERT INTO categories (id, name) VALUES (4, 'catagory4');