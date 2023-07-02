CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS spa
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    description     text NOT NULL
);

CREATE TABLE IF NOT EXISTS guest
(
    id        uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name      text NOT NULL,
    email     text NOT NULL
);

CREATE TABLE IF NOT EXISTS appointment
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    guestid     uuid,
    date        date NOT NULL,
    description text      NOT NULL,  
    spaid  uuid
);


