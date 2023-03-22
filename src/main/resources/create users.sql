DROP TABLE IF EXISTS warehouse.roles CASCADE;
CREATE TABLE warehouse.roles
(
    id SERIAL,    
    name VARCHAR(20) UNIQUE NOT NULL,    
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS warehouse.users CASCADE;
CREATE TABLE warehouse.users
(
    id SERIAL,    
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(15) NOT NULL,
    email VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    position VARCHAR(10) NOT NULL,
    role_id INT references warehouse.roles(id),
    PRIMARY KEY (id)
);

