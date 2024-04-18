DROP SCHEMA benevolo CASCADE;
CREATE SCHEMA benevolo;

CREATE TABLE address
(
    id      VARCHAR(255),
    street  VARCHAR(255),
    state   VARCHAR(255),
    city    VARCHAR(255),
    zip     VARCHAR(255),
    country VARCHAR(255),
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE event
(
    id          VARCHAR(255),
    event_name  VARCHAR(255),
    starts_at   TIMESTAMP,
    ends_at     TIMESTAMP,
    description TEXT,
    address_id  VARCHAR(255),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_address FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE ticket_type
(
    id               VARCHAR(256),
    ticket_type_name VARCHAR(256),
    price            INTEGER,
    tax_rate         INTEGER,
    valid_from       TIMESTAMP,
    valid_to         TIMESTAMP,
    capacity         INTEGER,
    active           BOOLEAN,
    event_id         VARCHAR(256),
    CONSTRAINT pk_ticket_type PRIMARY KEY (id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event (id)
);
