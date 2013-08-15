# Venue schema

# --- !Ups

CREATE TABLE venue (
    id varchar(255) NOT NULL UNIQUE,
    name varchar(255),
    address1 varchar(100),
    address2 varchar(100),
    city varchar(50),
    state varchar(50),
    postal_code varchar(20),
    url varchar(255)
);
CREATE INDEX ix_venue_id ON venue(id);
CREATE INDEX ix_venue_name ON venue(name);

# --- !Downs

DROP TABLE venue;