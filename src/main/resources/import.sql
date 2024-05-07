DROP TABLE IF EXISTS event CASCADE;
DROP TABLE IF EXISTS address CASCADE;

CREATE TABLE address (
     id VARCHAR(255),
     street VARCHAR(255),
     state VARCHAR(255),
     city VARCHAR(255),
     zip VARCHAR(255),
     country VARCHAR(255),
     CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE event (
    id VARCHAR(255),
    event_name VARCHAR(255),
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    description TEXT,
    picture BYTEA DEFAULT NULL,
    address_id VARCHAR(255),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE ticket_type (
    id VARCHAR(256),
    ticket_type_name VARCHAR(256),
    price INTEGER,
    tax_rate INTEGER,
    valid_from TIMESTAMP,
    valid_to TIMESTAMP,
    capacity INTEGER,
    active BOOLEAN,
    event_id VARCHAR(256),
    CONSTRAINT pk_ticket_type PRIMARY KEY (id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event(id)
);

INSERT INTO address(id, street, state, city, zip, country)
VALUES ('a83f700f-5449-4e40-b509-bee0b5d139d6', 'Münchner Straße', 'Bayern', 'Ingolstadt', '85049', 'DE');

INSERT INTO event(id, event_name, starts_at, ends_at, description, address_id)
VALUES ('383f700f-5449-4e40-b509-bee0b5d139d6', 'Weed Party 420', '2003-04-12 04:05:06', '2005-04-12 04:05:06',
        'Smoke weed everyday', 'a83f700f-5449-4e40-b509-bee0b5d139d6');

INSERT INTO ticket_type(id, ticket_type_name, price, tax_rate, valid_from, valid_to, capacity, active, event_id)
VALUES ('223f700f-5449-4e40-b509-bee0b5d139d6', 'Samstagsticket', 2500, 7, '2022-04-12 04:05:06', '2022-04-16 04:05:06',
        200, true, '383f700f-5449-4e40-b509-bee0b5d139d6'),
       ('b23f700f-5449-4e40-b509-bee0b5d139d6', 'Sonntagsticket', 3000, 7, '2022-04-12 04:05:06', '2022-04-16 04:05:06',
        100, true, '383f700f-5449-4e40-b509-bee0b5d139d6');