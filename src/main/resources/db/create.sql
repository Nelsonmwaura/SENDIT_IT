SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS destinations (
    id int PRIMARY KEY auto_increment,
    destinationName VARCHAR,
    nearestStage VARCHAR
);
