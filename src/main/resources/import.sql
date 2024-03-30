CREATE TABLE event (
    id VARCHAR(256),
    event_name VARCHAR(256),
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    CONSTRAINT pk_event PRIMARY KEY (id)
);