DROP TABLE IF EXISTS warehouse.partners CASCADE;
CREATE TABLE warehouse.partners
(
    id SERIAL,    
    name VARCHAR(20)  NOT NULL,
    TIN VARCHAR(10) UNIQUE NOT NULL,
    address VARCHAR(50)  NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(20)  NOT NULL,
    type VARCHAR(1) NOT NULL,
    PRIMARY KEY (id)
);
DROP TABLE IF EXISTS warehouse.groups CASCADE;
CREATE TABLE warehouse.groups
(
    id SERIAL,
    name VARCHAR(15) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);
DROP TABLE IF EXISTS warehouse.items CASCADE;
CREATE TABLE warehouse.items
(
    id SERIAL,
    sku VARCHAR(15) NOT NULL,
    name VARCHAR(15) NOT NULL,
    location VARCHAR(10) NOT NULL,
    group_id INT NOT NULL references warehouse.groups(id),
    unit VARCHAR(10) NOT NULL,
    price DECIMAL NOT NULL,
    PRIMARY KEY (id)
);
DROP TABLE IF EXISTS warehouse.transactions CASCADE;
CREATE TABLE warehouse.transactions
(
     id BIGSERIAL,
     type VARCHAR(3) NOT NULL, 
     date date NOT NULL,
     partner_id INT NOT NULL references warehouse.partners(id),
     item_id INT NOT NULL references warehouse.items(id),
     quantity DECIMAL NOT NULL,
     total_price DECIMAL NOT NULL,
     PRIMARY KEY (id)
);
DROP TABLE IF EXISTS warehouse.stock_balance CASCADE;
CREATE TABLE warehouse.stock_balance
(
     id SERIAL,
     date date NOT NULL,
     item_id INT NOT NULL references warehouse.items(id),
     stock_remainder DECIMAL NOT NULL,
     PRIMARY KEY (id)
);

INSERT INTO warehouse.groups (id, name) VALUES (1, 'group1');

INSERT INTO warehouse.items (id, sku, name, location, group_id, unit, price) VALUES (1, 8830329807, 'item1', 'G6', 1, 'M', 1490.7);

INSERT INTO warehouse.partners (id, name,TIN, address, phone, email, type) VALUES (1, 'partner1', '3350754345', 'address1', '+8303231056', 'partner1@gmail.com', 'C');

INSERT INTO warehouse.transactions (id, type, date, partner_id, item_id, quantity, total_price) VALUES (1, 'IN', '2022-08-22', 1, 1, 10, 1490.7);

INSERT INTO warehouse.stock_balance (id, date, item_id, stock_remainder) VALUES (1, '2022-08-22', 1, 91);
INSERT INTO warehouse.stock_balance (id, date, item_id, stock_remainder) VALUES (2, '2022-08-23', 1, 204);
INSERT INTO warehouse.stock_balance (id, date, item_id, stock_remainder) VALUES (3, '2022-08-24', 1, 782);
INSERT INTO warehouse.stock_balance (id, date, item_id, stock_remainder) VALUES (4, '2022-08-25', 1, 104);