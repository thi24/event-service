DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS address;


CREATE TABLE address (
     id VARCHAR(255),
     street VARCHAR(255),
     state VARCHAR(255),
     city VARCHAR(255),
     zip VARCHAR(255),
     country VARCHAR(255),
     event_id VARCHAR(255),
     CONSTRAINT pk_address PRIMARY KEY (id),
     CONSTRAINT fk_address_event FOREIGN KEY (event_id) REFERENCES event(id)
);

CREATE TABLE event (
    id VARCHAR(255),
    event_name VARCHAR(255),
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    description TEXT,
    address_id VARCHAR(255),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_address FOREIGN KEY (address_id) REFERENCES address(id)
);


