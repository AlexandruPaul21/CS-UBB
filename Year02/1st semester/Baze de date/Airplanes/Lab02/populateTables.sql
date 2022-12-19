--populating tables
USE Airlines226
GO

INSERT INTO Counties(nameCo) VALUES ('Salaj'),
                                    ('Bihor'),
                                    ('Maramures'),
                                    ('Satu-Mare'),
                                    ('Bucuresti'),
                                    ('Craiova'),
                                    ('Constanta'),
                                    ('Vaslui'),
                                    ('Timis'),
                                    ('Sibiu'),
                                    ('Brasov');

INSERT INTO Counties(nameCo) VALUES ('Cluj');

INSERT INTO Cities(nameCi, county) VALUES ('Zalau',1),
                                          ('Jibou',1),
                                          ('Oradea',2),
                                          ('Baia Mare',3),
                                          ('Satu Mare',4),
                                          ('Craiova',6),
                                          ('Constanta',7),
                                          ('Timisoara',9),
                                          ('Sibiu',10),
                                          ('Cluj-Napoca',12),
                                          ('Jucu',12),
                                          ('Topa',12);
GO

INSERT INTO Streets(nameS, number, city) VALUES ('Simion Barnutiu',29,1),
                                                ('Principala',3,2),
                                                ('Kossuth',32,3),
                                                ('Mare',103,5),
                                                ('Decebal',1,6),
                                                ('Casion',5,7),
                                                ('Bega',15,8),
                                                ('Martin Luther',20,8),
                                                ('Pietonala',30,9),
                                                ('Nicolae Iorga',69,9),
                                                ('Cisnadie',87,9),
                                                ('22 decembrie',32,10),
                                                ('BP Hasdeu',78,10),
                                                ('Observatorului',75,10),
                                                ('Teodor Mihali',32,10),
                                                ('Princiapala',3,11);
GO

INSERT INTO Airports(nameA, id_address, max_class) VALUES
                                                       ('Avram Iancu',12,5),
                                                       ('Schipol',1,10),
                                                       ('Heatrow',5,10),
                                                       ('Lutton',6,4),
                                                       ('EHS Frankfurt',10,10),
                                                       ('Otopeni',9,8),
                                                       ('Chicago',16,6),
                                                       ('NY Air',11,4);
GO

INSERT INTO Garages(nameG, capacity, id_address) VALUES
                                                     ('Hangar Cluj',5,12),
                                                     ('Hangar Olanda',10,10),
                                                     ('Hangar Europa',20,6),
                                                     ('Hangar America',10,16),
                                                     ('Hangar Africa',5,11);
GO

INSERT INTO Airplanes(model, capacity, registration, id_gar) VALUES
                                                                 ('Boeing 737',200,'YR-AAA',1),
                                                                 ('Boeing 747',600,'G-AAAA',2),
                                                                 ('Boeing 767',450,'EC-WZZ',3),
                                                                 ('Airbus A320',200,'YR-BAA',1),
                                                                 ('Airbus A380',780,'G-AAAC',2),
                                                                 ('Airbus A340',300,'EC-EZZ',3),
                                                                 ('Bombardier',150,'HB-ACC',1),
                                                                 ('Airbus Beluga',500,'HB-AZZ',4),
                                                                 ('Antonov RIP',300,'YR-ASA',5);
GO


INSERT INTO AuxiliaryPer(nameAP, birthdate, height, weight, gender, id_address) VALUES
                                                ('Tabatha Andrews',datetimefromparts(2002,6,12,10,34,9,0),165,76,0,1),
                                                ('Willa Perkins',datetimefromparts(1986,4,30,10,34,9,0),156,55,0,2),
                                                ('Wes Huang',datetimefromparts(1990,5,23,10,34,9,0),186,78,1,3),
                                                ('Harriet Lynch',datetimefromparts(2001,10,12,10,34,9,0),175,90,0,2),
                                                ('Pearl Hamilton',datetimefromparts(1984,3,27,10,34,9,0),170,94,0,4),
                                                ('Lucile Mooney',datetimefromparts(2000,3,28,10,34,9,0),160,52,0,6),
                                                ('Tamika Mccullough',datetimefromparts(2002,4,21,10,34,9,0),169,69,0,4),
                                                ('James Hahn',datetimefromparts(1999,8,14,10,34,9,0),176,73,0,15),
                                                ('Lemuel Allen',datetimefromparts(1986,6,29,10,34,9,0),198,100,1,16);
GO

INSERT INTO Pilots(nameP, birthdate, height, weight, gender, id_address, id_air) VALUES
                                                ('Joaquin Davidson',datetimefromparts(1987,6,12,10,34,9,0),145,66,1,1,1),
                                                ('Osvaldo Dickson',datetimefromparts(1986,5,30,10,34,9,0),176,54,1,2,2),
                                                ('Lou Bush',datetimefromparts(1950,5,15,10,34,9,0),154,58,1,3,4),
                                                ('Len Huerta',datetimefromparts(2002,9,11,10,34,9,0),183,70,1,4,5),
                                                ('Charlene Patrick',datetimefromparts(1983,3,26,10,34,9,0),190,64,0,6,2),
                                                ('Andrew Hensley',datetimefromparts(2000,3,28,10,34,9,0),160,92,1,7,4),
                                                ('Vaughn May',datetimefromparts(2002,4,21,10,34,9,0),179,79,1,12,8),
                                                ('James Hahn',datetimefromparts(1949,8,14,10,34,9,0),156,83,1,1,9),
                                                ('Carlton Lucero',datetimefromparts(1980,6,28,10,34,9,0),178,100,1,16,5);
GO

INSERT INTO Pilots(nameP, birthdate, height, weight, gender, id_address, id_air) VALUES
                                                ('Kay Richards',datetimefromparts(1954,7,13,10,34,9,0),155,76,1,5,1),
                                                ('Sondra Hudson',datetimefromparts(1963,6,1,10,34,9,0),166,64,0,8,2),
                                                ('Hallie Meyers',datetimefromparts(1979,7,16,10,34,9,0),164,68,0,9,4),
                                                ('Jason Bray',datetimefromparts(2002,10,21,10,34,9,0),173,80,1,5,6),
                                                ('Alphonse Alvarez',datetimefromparts(1993,4,16,10,34,9,0),180,74,1,1,2),
                                                ('Abraham Donaldson',datetimefromparts(2010,4,18,10,34,9,0),170,82,1,2,4),
                                                ('Angelica Wilcox',datetimefromparts(1902,5,22,10,34,9,0),189,89,0,13,8);
GO

INSERT INTO Team(id_pilot, id_aux) VALUES
                                       (1,25),
                                       (2,27),
                                       (3,28),
                                       (3,29),
                                       (4,30),
                                       (5,30),
                                       (6,25),
                                       (7,31),
                                       (9,32),
                                       (1002,33),
                                       (1003,34),
                                       (1004,27),
                                       (1005,31),
                                       (1006,33),
                                       (1007,25),
                                       (5,33);
GO

INSERT INTO Passengers(nameP, birthdate, gender, id_address) VALUES
                                        ('Lou Briggs',datetimefromparts(1999,1,30,10,34,9,0),0,1),
                                        ('Lorena Fletcher',datetimefromparts(1998,2,27,10,34,9,0),0,3),
                                        ('Rudy Soto',datetimefromparts(1997,3,28,10,34,9,0),1,2),
                                        ('Nathan Burke',datetimefromparts(1989,4,26,10,34,9,0),1,5),
                                        ('Iva Archer',datetimefromparts(1979,5,15,10,34,9,0),0,4),
                                        ('Elvia Stevens',datetimefromparts(1969,6,14,10,34,9,0),0,7),
                                        ('Jerald Meyers',datetimefromparts(2002,7,13,10,34,9,0),1,14),
                                        ('Emil Krause',datetimefromparts(2012,1,6,10,34,9,0),1,8),
                                        ('Jimmie Clarke',datetimefromparts(2020,2,12,10,34,9,0),1,9),
                                        ('Branden Jones',datetimefromparts(1954,10,31,10,34,9,0),1,11),
                                        ('Shaun Schultz',datetimefromparts(1956,11,30,10,34,9,0),1,13),
                                        ('Ashley Tate',datetimefromparts(1973,11,1,10,34,9,0),0,14),
                                        ('Gene Carrillo',datetimefromparts(1975,12,6,10,34,9,0),1,16);
GO

INSERT INTO Routes(id_from, id_to, comfort_level, duration) VALUES
                                        (1,3,3,100),
                                        (1,5,2,120),
                                        (2,3,3,90),
                                        (4,6,1,360),
                                        (1,6,3,60),
                                        (5,7,2,600),
                                        (4,2,3,100),
                                        (2,5,1,50),
                                        (7,5,2,80);
GO

INSERT INTO Flights(id_route, id_team, fl_date, price) VALUES
                                        (1,1,datetimefromparts(2022,11,22,12,0,0,0),100),
                                        (2,3,datetimefromparts(2023,2,21,10,30,0,0),150),
                                        (3,5,datetimefromparts(2022,12,10,11,30,0,0),200),
                                        (4,7,datetimefromparts(2023,3,17,15,0,0,0),1000),
                                        (3,9,datetimefromparts(2023,4,11,17,0,0,0),150),
                                        (5,2,datetimefromparts(2023,12,18,22,0,0,0),160),
                                        (5,4,datetimefromparts(2022,11,12,14,0,0,0),200),
                                        (7,6,datetimefromparts(2023,5,1,7,0,0,0),25),
                                        (8,8,datetimefromparts(2023,1,14,6,0,0,0),300),
                                        (7,11,datetimefromparts(2022,12,2,4,30,0,0),40),
                                        (9,13,datetimefromparts(2023,3,16,6,30,0,0),2500),
                                        (2,14,datetimefromparts(2022,12,5,3,30,0,0),1000),
                                        (3,14,datetimefromparts(2022,11,7,12,35,0,0),400),
                                        (1,5,datetimefromparts(2022,12,10,14,25,0,0),3000);
GO

INSERT INTO Boardings(id_flight, id_pas) VALUES
                                        (1,1),
                                        (1,2),
                                        (1,3),
                                        (2,2),
                                        (2,3),
                                        (3,4),
                                        (3,5),
                                        (3,6),
                                        (4,7),
                                        (4,10),
                                        (5,11),
                                        (5,12),
                                        (5,13),
                                        (6,8),
                                        (6,9),
                                        (7,10),
                                        (7,5),
                                        (8,6),
                                        (8,7),
                                        (8,8),
                                        (9,8),
                                        (9,9),
                                        (11,11),
                                        (11,12),
                                        (11,13),
                                        (12,1),
                                        (12,2),
                                        (12,3),
                                        (12,4),
                                        (12,5),
                                        (13,6),
                                        (13,10),
                                        (13,11),
                                        (13,5),
                                        (14,6);

















