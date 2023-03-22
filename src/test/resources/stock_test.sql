DROP TABLE IF EXISTS stock_Balance CASCADE;
CREATE TABLE stock_Balance
(
     id SERIAL,
     date date NOT NULL,
     item_id INT NOT NULL references items(id),
     stock_remainder DECIMAL NOT NULL,
     PRIMARY KEY (id)
);
DROP TABLE IF EXISTS items CASCADE;
CREATE TABLE items
(
    id SERIAL,
    sku VARCHAR(15) NOT NULL,
    name VARCHAR(15) NOT NULL,
    location VARCHAR(10) NOT NULL,
    group_id INT NOT NULL references groups(id),
    unit VARCHAR(10) NOT NULL,
    price DECIMAL NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups
(
    id SERIAL,
    name VARCHAR(15) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO groups (id, name) VALUES (1, 'group1');

INSERT INTO items (id, sku, name, location, group_id, unit, price) VALUES (1, 8830329807, 'item1', 'G6', 1, 'M', 1490.7);

INSERT INTO stock_Balance (id, date, item_id, stock_remainder) VALUES (1, '2022-08-22', 1, 91);
INSERT INTO stock_Balance (id, date, item_id, stock_remainder) VALUES (2, '2022-08-23', 1, 204);
INSERT INTO stock_Balance (id, date, item_id, stock_remainder) VALUES (3, '2022-08-24', 1, 782);
INSERT INTO stock_Balance (id, date, item_id, stock_remainder) VALUES (4, '2022-08-25', 1, 104);