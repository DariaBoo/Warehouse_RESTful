--Categories table
INSERT INTO warehouse.categories (name) VALUES ('Electronics');
INSERT INTO warehouse.categories (name) VALUES ('Clothing');
INSERT INTO warehouse.categories (name) VALUES ('Furniture');
INSERT INTO warehouse.categories (name) VALUES ('Sports');
INSERT INTO warehouse.categories (name) VALUES ('Beauty');
INSERT INTO warehouse.categories (name) VALUES ('Toys');
INSERT INTO warehouse.categories (name) VALUES ('Food');
INSERT INTO warehouse.categories (name) VALUES ('Books');
INSERT INTO warehouse.categories (name) VALUES ('Music');
INSERT INTO warehouse.categories (name) VALUES ('Home Decor');

--Units table
INSERT INTO warehouse.units (name) VALUES ('KILOGRAM');
INSERT INTO warehouse.units (name) VALUES ('GRAM');
INSERT INTO warehouse.units (name) VALUES ('LITER');
INSERT INTO warehouse.units (name) VALUES ('MILLILITER');
INSERT INTO warehouse.units (name) VALUES ('METER');
INSERT INTO warehouse.units (name) VALUES ('CENTIMETER');
INSERT INTO warehouse.units (name) VALUES ('PIECE');
INSERT INTO warehouse.units (name) VALUES ('BOX');

--Items table
INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price, reorder_quantity)
VALUES ('PROD001', 'Product A', 'This is a description for Product A', 'A-01', 1, 1, 19.99, 100);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price)
VALUES ('PROD002', 'Product B', 'This is a description for Product B', 'A-02', 2, 2, 29.99);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price, reorder_quantity)
VALUES ('PROD003', 'Product C', 'This is a description for Product C', 'B-01', 3, 3, 39.99, 50);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price)
VALUES ('PROD004', 'Product D', 'This is a description for Product D', 'B-02', 4, 4, 49.99);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price, reorder_quantity)
VALUES ('PROD005', 'Product E', 'This is a description for Product E', 'C-01', 5, 5, 59.99, 75);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price)
VALUES ('PROD006', 'Product F', 'This is a description for Product F', 'C-02', 6, 6, 69.99);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price, reorder_quantity)
VALUES ('PROD007', 'Product G', 'This is a description for Product G', 'D-01', 7, 7, 79.99, 25);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price)
VALUES ('PROD008', 'Product H', 'This is a description for Product H', 'D-02', 8, 8, 89.99);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price, reorder_quantity)
VALUES ('PROD009', 'Product I', 'This is a description for Product I', 'E-01', 9, 4, 99.99, 30);

INSERT INTO warehouse.items (code, name, description, location, category_id, unit_id, selling_price)
VALUES ('PROD010', 'Product J', 'This is a description for Product J', 'E-02', 10, 5, 109.99);


--Partners table
INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, discount_percent, type, notes)
VALUES ('John Doe', '1234567890', 'Credit Card', 'Net 30', 5.5, 'SUPPLIER', 'This is a note about John Doe');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, discount_percent, type, notes)
VALUES ('Jane Smith', '2345678901', 'ACH', 'Net 15', 1, 'SUPPLIER', 'This is a note about Jane Smith');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, discount_percent, type, notes)
VALUES ('Acme Inc.', '3456789012', 'Wire Transfer', 'Net 60', 10, 'CUSTOMER', 'This is a note about Acme Inc.');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, type, notes)
VALUES ('XYZ Company', '4567890123', 'Check', 'Due on receipt', 'CUSTOMER', 'This is a note about XYZ Company');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, discount_percent, type, notes)
VALUES ('ABC Corporation', '5678901234', 'PayPal', 'Net 45', 15, 'CUSTOMER', 'This is a note about ABC Corporation');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, type, notes)
VALUES ('Acme LLC', '6789012345', 'Credit Card', 'Due on receipt', 'SUPPLIER', 'This is a note about Acme LLC');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, discount_percent, type, notes)
VALUES ('John Smith', '7890123456', 'Wire Transfer', 'Net 30', 3.5, 'SUPPLIER', 'This is a note about John Smith');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, type, notes)
VALUES ('XYZ Corporation', '8901234567', 'Check', 'Due on receipt', 'CUSTOMER', 'This is a note about XYZ Corporation');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, discount_percent, type, notes)
VALUES ('ABC Inc.', '9012345678', 'ACH', 'Net 15', 5, 'CUSTOMER', 'This is a note about ABC Inc.');

INSERT INTO warehouse.partners (name, TIN, payment_method, payment_terms, type, notes)
VALUES ('Acme Company', '0123456789', 'PayPal', 'Due on receipt', 'SUPPLIER', 'This is a note about Acme Company');

--Addresses table
INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (1, 'John Doe', '123 Main St', 'New York', 'NY', 'USA', '10001', '555-1234', 'jdoe@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (1, 'Jane Smith', '456 Broadway', 'New York', 'NY', 'USA', '10002', '555-5678', 'jsmith@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (2, 'Samantha Johnson', '789 Fifth Ave', 'Los Angeles', 'CA', 'USA', '90001', '555-2468', 'sjohnson@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (2, 'Robert Hernandez', '1010 Wilshire Blvd', 'Los Angeles', 'CA', 'USA', '90002', '555-3691', 'rhernandez@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (3, 'Emily Wong', '1234 Market St', 'San Francisco', 'CA', 'USA', '94102', '555-7890', 'ewong@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (3, 'David Lee', '5678 Geary Blvd', 'San Francisco', 'CA', 'USA', '94103', '555-2468', 'dlee@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (4, 'Alex Kim', '9012 Mission St', 'San Jose', 'CA', 'USA', '95101', '555-3691', 'akim@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (4, 'Julia Chen', '3456 Stevens Creek Blvd', 'San Jose', 'CA', 'USA', '95102', '555-7890', 'jchen@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (5, 'Mark Davis', '7890 University Ave', 'Austin', 'TX', 'USA', '78701', '555-2468', 'mdavis@example.com');

INSERT INTO warehouse.addresses (partner_id, contact_person, address, city, state, country, postal_code, phone, email)
VALUES (5, 'Karen Johnson', '1234 Congress Ave', 'Austin', 'TX', 'USA', '78702', '555-3691', 'kjohnson@example.com');

--Stock_balance table
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 1, 100);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 2, 150);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 3, 200);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 4, 75);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 5, 300);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 6, 125);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 7, 50);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 8, 225);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 9, 175);
INSERT INTO warehouse.stock_balance (date, item_id, stock_remainder) VALUES ('2023-03-10', 10, 80);

--Transactions table
INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('PURCHASE', '2023-03-10', 1, 1, 10, 50.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('PURCHASE', '2023-03-10', 2, 2, 5, 100.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('PURCHASE', '2023-03-10', 3, 3, 15, 75.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('SALES', '2023-03-10', 4, 4, -8, 200.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('SALES', '2023-03-10', 5, 5, -2, 300.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('RETURN', '2023-03-10', 6, 6, 5, 150.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('TRANSFER_FROM', '2023-03-10', 7, 7, -12, 50.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('TRANSFER_TO', '2023-03-10', 8, 8, 10, 100.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('TRANSFER_TO', '2023-03-10', 9, 9, 8, 150.00);

INSERT INTO warehouse.transactions (type, date, partner_id, item_id, quantity, unit_price)
VALUES ('TRANSFER_FROM', '2023-03-10', 10, 10, -3, 75.00);




