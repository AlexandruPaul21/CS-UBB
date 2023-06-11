use master
if exists(select * from sys.databases where name='politie')
	drop database politie
create database politie
go
use politie

create table Sectii(
	id int primary key,
	denumire varchar(30) null
);
go

create table Grade(
	id int primary key,
	denumire varchar(50) null,
	descriere varchar(1000) null
);
go

create table Functii(
	id int primary key,
	denumire varchar(50) null,
	descriere varchar(1000) null
);
go

create table Sectoare(
	id int primary key,
	denumire varchar(50) null,
	descriere varchar(1000) null
);
go

create table Politisti(
	id int primary key,
	nume varchar(50) null,
	prenume varchar(50) null,
	id_sectie int constraint FK_Sectii_Politisti foreign key references Sectii(id),
	id_grad int constraint FK_Grade_Politisti foreign key references Grade(id),
	id_functie int constraint FK_Functii_Politisti foreign key references Functii(id),
);
go

create table Programari(
	id int primary key,
	id_politist int constraint FK_Politisti_Programari foreign key references Politisti(id),
	id_sector int constraint FK_Sectoare_Programari foreign key references Sectoare(id),
	data date,
	ora varchar
);
go

--Sectii
INSERT INTO Sectii (id,denumire) VALUES (1,'Sectia 1');
INSERT INTO Sectii (id,denumire) VALUES (2,'Sectia 2');
INSERT INTO Sectii (id,denumire) VALUES ('3','Sectia 3');
--Grade
INSERT INTO Grade (id,denumire,descriere) VALUES
(1,'Comisar sef','Ofiter de politie');
INSERT INTO Grade (id,denumire,descriere) VALUES
(2,'Inspector','Ofiter de politie');
INSERT INTO Grade (id,denumire,descriere) VALUES
(3,'Agent principal','Agent');
--Functii
INSERT INTO Functii (id,denumire,descriere) VALUES
(1,'Ofiter specialist principal','');
INSERT INTO Functii (id,denumire,descriere) VALUES
(2,'Ofiter specialist','');
INSERT INTO Functii (id,denumire,descriere) VALUES
(3,'Agent specialist','');
--Sectoare
INSERT INTO Sectoare (id,denumire,descriere) VALUES
(1,'Sector 1','');
INSERT INTO Sectoare (id,denumire,descriere) VALUES
(2,'Sector 2','');
INSERT INTO Sectoare (id,denumire,descriere) VALUES
(3,'Sector 3','');
--Politisti
INSERT INTO Politisti(id,nume,prenume,id_sectie,id_grad,id_functie) VALUES
(1,'Pop','Ion',1,1,1);
INSERT INTO Politisti(id,nume,prenume,id_sectie,id_grad,id_functie) VALUES
(2,'Ionescu','Dan',2,2,2);
INSERT INTO Politisti(id,nume,prenume,id_sectie,id_grad,id_functie) VALUES
(3,'Poanta','Isac',3,3,3);
--Programari
INSERT INTO Programari(id,id_politist,id_sector,data,ora) VALUES
(1,1,1,'05/20/2022','08:00:00');
INSERT INTO Programari(id,id_politist,id_sector,data,ora) VALUES
(2,2,1,'05/21/2022','08:00:00');
INSERT INTO Programari(id,id_politist,id_sector,data,ora) VALUES
(3,3,2,'05/20/2022','08:00:00');
INSERT INTO Programari(id,id_politist,id_sector,data,ora) VALUES
(4,1,3,'05/22/2022','08:00:00');