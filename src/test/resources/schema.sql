CREATE SCHEMA IF NOT EXISTS warehouse AUTHORIZATION sa;

DROP TABLE IF EXISTS warehouse.groups CASCADE;
CREATE TABLE warehouse.groups
(
    id SERIAL,
    name VARCHAR(15) UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO warehouse.groups (name) VALUES ('group1');
INSERT INTO warehouse.groups (name) VALUES ('group2');
INSERT INTO warehouse.groups (name) VALUES ('group3');
INSERT INTO warehouse.groups (name) VALUES ('group4');