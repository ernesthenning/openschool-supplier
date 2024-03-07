DROP TABLE IF EXISTS category CASCADE ;
DROP TABLE IF EXISTS product CASCADE ;
DROP SEQUENCE IF EXISTS global_seq ;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE category
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name      VARCHAR NOT NULL
);


CREATE TABLE product
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    price       FLOAT NOT NULL,
    category_id INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

