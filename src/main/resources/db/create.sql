SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS deliveries (
    id int PRIMARY KEY auto_increment,
    item VARCHAR,
    quantity VARCHAR,
    price INTEGER,
    destination VARCHAR
);
