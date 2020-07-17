CREATE TABLE pharmacies
(
    id SERIAL PRIMARY KEY,
    name varchar(30) NOT NULL,
    address varchar(30) NOT NULL,
    city varchar(40) NOT NULL,
    state      varchar(50) NOT NULL,
    zip       varchar(50) NOT NULL,
    latitude DECIMAL(8,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
);