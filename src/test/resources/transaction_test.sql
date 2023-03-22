DROP TABLE IF EXISTS transactions CASCADE;
CREATE TABLE transactions
(
     id BIGSERIAL,
     type VARCHAR(3) NOT NULL, 
     date date NOT NULL,
     partner_id INT NOT NULL references partners(id),
     item_id INT NOT NULL references items(id),
     quantity DECIMAL NOT NULL,
     total_price DECIMAL NOT NULL,
     PRIMARY KEY (id)
);
DROP TABLE IF EXISTS partners CASCADE;
CREATE TABLE partners
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

INSERT INTO partners (id, name,TIN, address, phone, email, type) VALUES (1, 'partner1', '3350754345', 'address1', '+8303231056', 'partner1@gmail.com', 'C');

INSERT INTO transactions (id, type, date, partner_id, item_id, quantity, total_price) VALUES (1, 'IN', '2022-08-22', 1, 1, 10, 1490.7);
