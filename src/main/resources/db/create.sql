--CREATE DATABASE users_database;
--
--CREATE TABLE users(
--id SERIAL PRIMARY KEY,
--adrress int,
--phone_number int,
--name VARCHAR,
--);

SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS users (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    address VARCHAR,
    phone_number INTEGER
);