CREATE DATABASE Airlines226
GO
USE Airlines226
GO

CREATE TABLE Counties(
    id INT PRIMARY KEY IDENTITY,
    nameCo VARCHAR(50) NOT NULL
)

CREATE TABLE Cities(
    id INT PRIMARY KEY IDENTITY(1,1),
    nameCi VARCHAR(50) NOT NULL,
    county INT FOREIGN KEY REFERENCES Counties(id)
)

CREATE TABLE Streets(
    id INT PRIMARY KEY IDENTITY ,
    nameS VARCHAR(50) NOT NULL ,
    number INT NOT NULL ,
    city INT FOREIGN KEY REFERENCES Cities(id)
)

CREATE TABLE Airports(
    id_air INT PRIMARY KEY IDENTITY ,
    nameA VARCHAR(50) NOT NULL,
    id_address INT FOREIGN KEY REFERENCES Streets(id) ,
    max_class INT
)

CREATE TABLE Garages(
    id_gar INT PRIMARY KEY IDENTITY,
    nameG VARCHAR(50) NOT NULL,
    capacity INT,
    id_address INT FOREIGN KEY REFERENCES Streets(id)
)

CREATE TABLE Airplanes(
    id_air INT PRIMARY KEY IDENTITY,
    model VARCHAR(50) NOT NULL,
    capacity INT,
    registration VARCHAR(50) NOT NULL,
    id_gar INT FOREIGN KEY REFERENCES Garages(id_gar)
)

CREATE TABLE AuxiliaryPer(
    id_per INT PRIMARY KEY IDENTITY,
    nameAP VARCHAR(50) NOT NULL,
    birthdate DATETIME,
    height INT,
    weight INT,
    gender BIT NOT NULL,
    id_address INT FOREIGN KEY REFERENCES Streets(id)
)

CREATE TABLE Pilots(
    id_pilot INT PRIMARY KEY IDENTITY,
    nameP VARCHAR(50) NOT NULL,
    birthdate DATETIME,
    height INT,
    weight INT,
    gender BIT NOT NULL,
    id_address INT FOREIGN KEY REFERENCES Streets(id),
    id_air INT FOREIGN KEY REFERENCES Airplanes(id_air)
)

CREATE TABLE Passengers(
    id_pas INT PRIMARY KEY IDENTITY,
    nameP VARCHAR(50) NOT NULL,
    birthdate DATETIME NOT NULL,
    gender BIT NOT NULL,
    id_address INT FOREIGN KEY REFERENCES Streets(id)
)

CREATE TABLE Routes(
    id_route INT PRIMARY KEY IDENTITY,
    id_from INT FOREIGN KEY REFERENCES Airports(id_air),
    id_to INT FOREIGN KEY REFERENCES Airports(id_air),
    comfort_level INT,
    duration INT
)

CREATE TABLE Team(
    id_team INT PRIMARY KEY IDENTITY,
    id_pilot INT FOREIGN KEY REFERENCES Pilots(id_pilot),
    id_aux INT FOREIGN KEY REFERENCES AuxiliaryPer(id_per)
)

CREATE TABLE Flights(
    id_flight INT PRIMARY KEY IDENTITY,
    id_route INT FOREIGN KEY REFERENCES Routes(id_route),
    id_team INT FOREIGN KEY REFERENCES Team(id_team),
    fl_date DATETIME NOT NULL,
    price MONEY NOT NULL
)

CREATE TABLE Boardings(
    id_flight INT FOREIGN KEY REFERENCES Flights(id_flight),
    id_pas INT FOREIGN KEY REFERENCES Passengers(id_pas),
    CONSTRAINT pk_Board PRIMARY KEY (id_flight, id_pas)
)

CREATE TABLE Versions(
    id INT PRIMARY KEY IDENTITY,
    ver INT UNIQUE,
    applied_op VARCHAR(50),
    undo_op VARCHAR(50)
)
