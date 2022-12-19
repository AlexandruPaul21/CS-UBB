--for lab 2
--CONVERT(VARCHAR(10), p.birthdate, 105)
USE Airlines226
GO
--5 with where
SELECT P.nameP, P.gender ,S.nameS, S.number, C.nameCi FROM Passengers P
INNER JOIN Streets S on S.id = P.id_address
INNER JOIN Cities C on C.id = S.city
INNER JOIN Counties C2 on C2.id = C.county WHERE C2.nameCo = 'Cluj';
GO

SELECT A.model, A.capacity, A.registration, G.nameG,C2.nameCo FROM Airplanes A
INNER JOIN Garages G on G.id_gar = A.id_gar
INNER JOIN Streets S on S.id = G.id_address
INNER JOIN Cities C on C.id = S.city
INNER JOIN Counties C2 on C2.id = C.county WHERE A.capacity > 400
GO

SELECT P.nameP, P.gender, AP.nameAP, AP.gender FROM Team T
INNER JOIN AuxiliaryPer AP on AP.id_per = T.id_aux
INNER JOIN Pilots P on P.id_pilot = T.id_pilot WHERE P.gender = AP.gender
GO

SELECT A.nameA , A2.nameA  FROM Routes R
INNER JOIN Airports A on A.id_air = R.id_from
INNER JOIN Airports A2 on A2.id_air = R.id_from WHERE R.comfort_level <> 1
GO

SELECT P.nameP, P.gender FROM Passengers P
WHERE MONTH(P.birthdate) = 1
GO

--3 with group by
SELECT A.model, COUNT(*) AS users FROM Pilots P
INNER JOIN Airplanes A on A.id_air = P.id_air
GROUP BY A.model
GO

SELECT C2.nameCo, COUNT(*) AS nr FROM Passengers P
INNER JOIN Streets S on S.id = P.id_address
INNER JOIN Cities C on C.id = S.city
INNER JOIN Counties C2 on C2.id = C.county
GROUP BY C2.nameCo
GO

SELECT YEAR(F.fl_date) AS year, AVG(F.price) as price FROM Flights F
GROUP BY YEAR(F.fl_date)
GO

--2 with distinct
SELECT DISTINCT P.nameP FROM Boardings B
INNER JOIN Passengers P on P.id_pas = B.id_pas
INNER JOIN Flights F on B.id_flight = F.id_flight
INNER JOIN Routes R on R.id_route = F.id_route
INNER JOIN Airports A on A.id_air = R.id_to
WHERE A.nameA = 'Heatrow'
GO

SELECT DISTINCT P.nameP FROM Boardings B
INNER JOIN Pilots P on P.id_pilot = B.id_pas
INNER JOIN Flights F on F.id_flight = B.id_flight
WHERE YEAR(F.fl_date) = '2023'
GO

--2 with tables in m-n relation
SELECT P.nameP, F.fl_date FROM Boardings B
INNER JOIN Flights F on F.id_flight = B.id_flight
INNER JOIN Passengers P on P.id_pas = B.id_pas
WHERE F.price > 500
GO

SELECT P.nameP, AP.nameAP, MONTH(AP.birthdate) AS month FROM Team T
INNER JOIN Pilots P on P.id_pilot = T.id_pilot
INNER JOIN AuxiliaryPer AP on AP.id_per = T.id_aux
WHERE MONTH(P.birthdate) = MONTH(AP.birthdate)
GO

--2 with having
SELECT MONTH(F.fl_date) as month, AVG(F.price) as avg_price FROM Boardings B
INNER JOIN Flights F on F.id_flight = B.id_flight
GROUP BY MONTH(F.fl_date)
HAVING AVG(F.price) >= 200
GO

SELECT A.nameA, COUNT(*) as passagers FROM Boardings B
INNER JOIN Flights F on F.id_flight = B.id_flight
INNER JOIN Routes R2 on R2.id_route = F.id_route
INNER JOIN Airports A on A.id_air = R2.id_to
INNER JOIN Passengers P on P.id_pas = B.id_pas
GROUP BY A.nameA
HAVING COUNT(*) > 10












