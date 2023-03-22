CREATE SCHEMA IF NOT EXISTS warehouse AUTHORIZATION admin;

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

DROP TYPE IF EXISTS partner_type;
CREATE TYPE partner_type AS ENUM ('CUSTOMER', 'SUPPLIER');

DROP TABLE IF EXISTS warehouse.partners CASCADE;
CREATE TABLE warehouse.partners
(
    id SERIAL,    
    name VARCHAR(20) NOT NULL,
    TIN VARCHAR(10) UNIQUE NOT NULL,
    payment_method VARCHAR(20),
    payment_terms VARCHAR(20),
    discount_percent NUMERIC(5, 2),
    type partner_type NOT NULL,
    notes VARCHAR(100),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS warehouse.addresses CASCADE;
CREATE TABLE warehouse.addresses
(
    id SERIAL,
    partner_id INT NOT NULL references warehouse.partners(id),
    contact_person VARCHAR(30),
    address VARCHAR(50) NOT NULL,
    city VARCHAR(20) NOT NULL,
    state VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(30) NOT NULL,
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
 
DROP TYPE IF EXISTS transaction_type;
CREATE TYPE transaction_type AS ENUM ('PURCHASE', 'SALES', 'RETURN', 'TRANSFER_FROM', 'TRANSFER_TO');

DROP TABLE IF EXISTS warehouse.transactions CASCADE;
CREATE TABLE warehouse.transactions
(
     id BIGSERIAL,
     type transaction_type NOT NULL,
     date date NOT NULL,
     partner_id INT NOT NULL references warehouse.partners(id),
     item_id INT NOT NULL references warehouse.items(id),
     quantity NUMERIC(10,2) NOT NULL,
     unit_price NUMERIC(10,2) NOT NULL,
     CONSTRAINT check_positive_quantity
     CHECK ((quantity > 0 AND type IN ('PURCHASE', 'RETURN', 'TRANSFER_TO')) 
         OR 
         (quantity < 0 AND type IN ('SALES', 'TRANSFER_FROM'))),
     PRIMARY KEY (id)
);




