DROP TABLE IF EXISTS warehouse.categories CASCADE;
CREATE TABLE warehouse.categories
(
    id SERIAL,
    name VARCHAR(15) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS warehouse.units CASCADE;
CREATE TABLE warehouse.units
(
    id SERIAL,
    name VARCHAR(15) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS warehouse.items CASCADE;
CREATE TABLE warehouse.items
(
    id BIGSERIAL,
    code VARCHAR(15) NOT NULL,
    name VARCHAR(15) NOT NULL,
    description VARCHAR(100) NOT NULL,
    location VARCHAR(10) NOT NULL,
    category_id INT NOT NULL references warehouse.categories(id),
    unit_id INT NOT NULL references warehouse.units(id),
    selling_price DECIMAL NOT NULL,
    reorder_quantity DECIMAL,
    PRIMARY KEY (id)
);


INSERT INTO categories (id, name) VALUES (1, 'category1');

INSERT INTO units (id, name) VALUES (1, 'psc');

INSERT INTO warehouse.items (id, code, name, description, location, category_id, unit_id, selling_price, reorder_quantity)
VALUES (1, 'PROD001', 'Product A', 'This is a description for Product A', 'A-01', 1, 1, 19.99, 100);
INSERT INTO warehouse.items (id, code, name, description, location, category_id, unit_id, selling_price)
VALUES (2, 'PROD002', 'Product B', 'This is a description for Product B', 'A-02', 1, 1, 29.99);
