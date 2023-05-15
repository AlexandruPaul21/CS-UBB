CREATE TABLE agencies(
                         username VARCHAR PRIMARY KEY,
                         ag_name VARCHAR,
                         pass VARCHAR
);

CREATE TABLE clients(
                        id INTEGER PRIMARY KEY,
                        client_name VARCHAR,
                        address varchar
);

CREATE TABLE flights(
                        id INTEGER PRIMARY KEY,
                        destination VARCHAR,
                        departure_date_time timestamp,
                        airport varchar,
                        available_seats integer
);

CREATE TABLE bookings(
                         id INTEGER PRIMARY KEY,
                         id_flight INTEGER,
                         id_client INTEGER,
                         clients_name VARCHAR,
                         CONSTRAINT fk_flight
                             FOREIGN KEY (id_flight) REFERENCES flights(id),
                         CONSTRAINT fk_client
                             FOREIGN KEY (id_client) REFERENCES clients(id)
);