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

INSERT INTO partners (id, name,TIN, address, phone, email, type) VALUES (1, 'partner1', '3350754345', 'address1', '+8303231056', 'partner1@gmail.com', 'C');
INSERT INTO partners (id, name,TIN, address, phone, email, type) VALUES (2, 'partner2', '2662151275', 'address2', '+5966182592', 'partner2@gmail.com', 'C');
INSERT INTO partners (id, name,TIN, address, phone, email, type) VALUES (3, 'partner3', '3081193312', 'address3', '+7148837852', 'partner3@gmail.com', 'C');
INSERT INTO partners (id, name,TIN, address, phone, email, type) VALUES (4, 'partner4', '1887566278', 'address4', '+7899185092', 'partner4@gmail.com', 'S');