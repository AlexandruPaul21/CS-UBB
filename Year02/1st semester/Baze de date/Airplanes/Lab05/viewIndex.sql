USE Airlines226
GO

CREATE OR ALTER VIEW VFlights AS
    SELECT A.nameA,R2.duration,P.nameP, Flights.price FROM Flights
    INNER JOIN Routes R2 on R2.id_route = Flights.id_route
    INNER JOIN Airports A on A.id_air = R2.id_from
    INNER JOIN Team T on T.id_team = Flights.id_team
    INNER JOIN Pilots P on P.id_pilot = T.id_pilot
GO

if exists(SELECT name FROM sys.indexes WHERE name = 'N_idx_Routes_duration')
    drop index N_idx_Routes_duration on Routes
GO
create nonclustered index N_idx_Routes_duration on Routes(duration)
GO

CREATE OR ALTER VIEW VAirplanes AS
    SELECT A.model, COUNT(*) AS users FROM Pilots P
    INNER JOIN Airplanes A on A.id_air = P.id_air
    GROUP BY A.model
GO

if exists(SELECT name FROM sys.indexes WHERE name = 'N_idx_Airplanes_Info')
    drop index N_idx_Airplanes_Info on Airplanes
GO
CREATE NONCLUSTERED INDEX N_idx_Airplanes_Info on Airplanes(model)
GO

SELECT * FROM VFlights ORDER BY duration
GO
SELECT * FROM VAirplanes

