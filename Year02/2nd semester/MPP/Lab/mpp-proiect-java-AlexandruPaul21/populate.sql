INSERT INTO agencies(username, ag_name, pass) VALUES
                    ('myAgency1', 'Real agency', '1234'),
                    ('iAmReal123', 'Fake Agency', '1234'),
                    ('flowers345', 'Flowers Agency', '1234'),
                    ('lifeIsGood', 'Blitz Real Estate', '1234');

INSERT INTO clients(id, client_name, address) VALUES
                    (1, 'Andrei Pop', 'Aurel 5'),
                    (2, 'Michael Jos', 'Mihai 2'),
                    (3, 'John Popa', 'Marlyn 68'),
                    (4, 'Alex', 'Iazului 1'),
                    (5, 'Nume', 'Adresa'),
                    (6, 'Alex', 'Motilor 1');

INSERT INTO flights(id, destination, departure_date_time, airport, available_seats) VALUES
                    (1, 'Puerto Rico', '2023-03-17 21:00', 'Henri C', 180),
                    (2, 'Bucuresti', '2023-03-18 14:00', 'Otopeni', 200),
                    (3, 'Ibiza', '2023-06-03 10:00', 'Schipol', 280),
                    (4, 'Palma de Mallorca', '2022-05-06 07:00', 'Heatrow', 300);

INSERT INTO bookings(id, id_flight, id_client, clients_name) VALUES
                    (1, 3, 1, 'Polly'),
                    (2, 1, 2, 'Nico,Toni,Horsie,Ronnie'),
                    (3, 2, 3, 'Marco,Harry'),
                    (4, 1, 1, 'Marry,Rose,Sussie'),
                    (5, 2, 1, 'Ana,Maria,Diana'),
                    (6, 1, 4, 'Ana,Maria,Cristina'),
                    (7, 1, 5, 'Num1,Nume2,Nume3'),
                    (8, 1, 6, 'ALex,Alex,fjda');