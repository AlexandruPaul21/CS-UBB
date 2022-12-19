USE Airlines226
GO
--- Table TABLES --------------------------------------------------
INSERT INTO Tables(Name) VALUES
('Counties'),
('Passengers'),
('Boardings');
GO

--- Create VIEW-uri -------------------------------------------------
CREATE OR ALTER VIEW VCounties
AS
SELECT  C.nameCo
FROM Counties C
GO

CREATE OR ALTER VIEW VPassengers
AS
SELECT P.nameP, C.nameCi
FROM Passengers P
INNER JOIN Streets S on S.id = P.id_address
INNER JOIN Cities C on C.id = S.city
GO

CREATE OR ALTER VIEW VBoarding
AS
SELECT F.fl_date, COUNT(*) AS 'passengers'
FROM Boardings B
INNER JOIN Passengers P on P.id_pas = B.id_pas
INNER JOIN Flights F on F.id_flight = B.id_flight
GROUP BY F.fl_date
GO


--- Table VIEW ------------------------------------------------------
INSERT INTO Views VALUES
('VCounties'),
('VPassengers'),
('VBoarding')


--- Table Tests -----------------------------------------------------
INSERT INTO Tests(Name) VALUES
('Insert10'),
('Insert100'),
('Insert1000'),
('Delete10'),
('Delete100'),
('Delete1000'),
('Eval')
GO


--- Table TESTTABLES ------------------------------------------------
INSERT INTO TestTables VALUES
(1, 1, 10, 1),
(2, 1, 100, 1),
(3, 1, 1000, 1),
(1, 2, 10, 2),
(2, 2, 100, 2),
(3, 2 ,1000, 2),
(1, 3, 10, 3),
(2, 3, 100, 3),
(3, 3, 1000, 3),

(4, 1, 10, 3),
(5, 1, 100, 3),
(6, 1, 1000, 3),
(4, 2, 10, 2),
(5, 2, 100, 2),
(6, 2 ,1000, 2),
(4, 3, 10, 1),
(5, 3, 100, 1),
(6, 3, 1000, 1)
GO


--- Table TESTVIEWS ---------------------------------------------------
INSERT INTO TestViews VALUES
(7,1),
(7,2),
(7,3)
GO


--- Insert in table COUNTIES ---
CREATE or ALTER PROCEDURE InsertCounties (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @name VARCHAR(50)
	DECLARE @i int
	DECLARE @lastId int
	SET @name='RandomCountyName'
	SET @id=2000
	SET @i=1

	WHILE @i<=@rows
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = C.id FROM dbo.Counties C ORDER BY C.id DESC
		IF @lastId >2000
			SET @id=@lastId+1
        SET IDENTITY_INSERT Counties ON
		INSERT INTO Counties(id, nameCo) VALUES (@id, @name)
		SET IDENTITY_INSERT Counties OFF
        SET @i=@i+1
	END

END
GO

--- Delete from table COUNTIES ---
CREATE OR ALTER PROCEDURE DeleteCounties(@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int

	SET @id=2000
	SET @i=@rows

	WHILE @i>0
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = C.id FROM dbo.Counties C ORDER BY C.id DESC
		IF @lastId >@id
			SET @id=@lastId
		DELETE FROM Counties WHERE Counties.id=@id
		SET @i=@i-1
	END
END
GO

    --- Insert in table PASSENGERS ---
CREATE or ALTER PROCEDURE InsertPass (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @name VARCHAR(50)
	DECLARE @gender BIT
	DECLARE @birth DATETIME
	DECLARE @i int
	DECLARE @lastId int

	SET @name='RandomPassName'
	SET @gender = 1
	set @birth = '1900-01-01 15:00'
	SET @id=2000
	SET @i=1

	WHILE @i<=@rows
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = P.id_pas FROM dbo.Passengers P ORDER BY P.id_pas DESC
		IF @lastId >2000
			SET @id=@lastId+1
        SET IDENTITY_INSERT Passengers ON
        INSERT INTO Passengers(id_pas, nameP, birthdate, gender, id_address) VALUES (@id, @name, @birth, @gender, 1)
		SET IDENTITY_INSERT Passengers OFF
        SET @i=@i+1
	END

END
GO

--- Delete from table PASSENGERS ---
CREATE OR ALTER PROCEDURE DeletePass(@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int

	SET @id=2000
	SET @i=@rows

	WHILE @i>0
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = P.id_pas FROM dbo.Passengers P ORDER BY P.id_pas DESC
		IF @lastId >@id
			SET @id=@lastId
		DELETE FROM Passengers WHERE Passengers.id_pas=@id
		SET @i=@i-1
	END
END
GO

--- Insert in table BOARDINGS ---
CREATE or ALTER PROCEDURE InsertBoardings (@rows int)
AS
BEGIN
	DECLARE @i int
	SET @i=@rows

	exec InsertPass @rows
	DECLARE @idP int, @name VARCHAR(50), @birth DATETIME, @gender BIT, @adr INT;

	--- cursor
	DECLARE cursorPass CURSOR SCROLL FOR
	SELECT id_pas, nameP , birthdate, gender, id_address FROM Passengers;
	OPEN cursorPass;
	FETCH LAST FROM cursorPass INTO @idP, @name , @birth, @gender, @adr;

	WHILE @i>0 AND @@FETCH_STATUS=0
		BEGIN
		INSERT INTO Boardings VALUES (1,@idP)
		FETCH PRIOR FROM cursorPass INTO @idP, @name , @birth, @gender, @adr;
		SET @i=@i-1
	END

	CLOSE cursorPass;
	DEALLOCATE cursorPass;
END
GO

--- Delete from table BOARDINGS ---
CREATE OR ALTER PROCEDURE DeleteBoardings(@rows int)
AS
BEGIN
	DECLARE @idE int
	DECLARE @i int
	DECLARE @idP int

	SET @i=@rows
	SET @idE=1

	WHILE @i>0
	BEGIN
		SELECT TOP 1 @idP = P.id_pas FROM dbo.Passengers P ORDER BY P.id_pas DESC
		IF @idP >2000
		BEGIN
			DELETE FROM Boardings WHERE Boardings.id_flight=1 AND Boardings.id_pas=@idP
			exec DeletePass 1
			END
		SET @i=@i-1
	END
END
GO

--- Testele ------------------------------------------------------------
CREATE OR ALTER PROCEDURE Insert10 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='Counties'
	exec InsertCounties 10
	IF @Table='Passengers'
	exec InsertPass 10
	IF @Table='Boardings'
	exec InsertBoardings 10
	else PRINT 'Invalid name'
END
GO

CREATE OR ALTER PROCEDURE Insert100 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='Counties'
	exec InsertCounties 100
	IF @Table='Passengers'
	exec InsertPass 100
	IF @Table='Boardings'
	exec InsertBoardings 100
	else PRINT 'Invalid name'
END
GO

CREATE OR ALTER PROCEDURE Insert1000 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='Counties'
	exec InsertCounties 1000
	IF @Table='Passengers'
	exec InsertPass 1000
	IF @Table='Boardings'
	exec InsertBoardings 1000
	--else PRINT 'Invalid name'
END
GO

CREATE OR ALTER PROCEDURE Delete10 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='Counties'
	exec DeleteCounties 10
	IF @Table='Passengers'
	exec DeletePass 10
	IF @Table='Boardings'
	exec DeleteBoardings 10
	else PRINT 'Invalid name'
END
GO

CREATE OR ALTER PROCEDURE Delete100 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='Counties'
	exec DeleteCounties 100
	IF @Table='Passengers'
	exec DeletePass 100
	IF @Table='Boardings'
	exec DeleteBoardings 100
	else PRINT 'Invalid name'
END
GO

CREATE OR ALTER PROCEDURE Delete1000 (@Table VARCHAR(20))
AS
BEGIN
	IF @Table='Counties'
	exec DeleteCounties 1000
	IF @Table='Passengers'
	exec DeletePass 1000
	IF @Table='Boardings'
	exec DeleteBoardings 1000
	--else PRINT 'Invalid name'
END
GO

CREATE OR ALTER PROCEDURE Eval (@View VARCHAR(20))
AS
BEGIN
	IF @View='Counties'
	select * from VCounties
	IF @View='Passengers'
	select * from VPassengers
	IF @View='Boardings'
	select * from VBoarding
	--else PRINT 'Invalid name'
END
GO

--- Main procedure ----------------------------------------------
CREATE OR ALTER PROCEDURE Main (@Table VARCHAR(20))
AS
BEGIN
	DECLARE @t1 datetime, @t2 datetime, @t3 datetime
	DECLARE @desc NVARCHAR(2000)

	DECLARE @testInsert VARCHAR(20)
	DECLARE @testDelete VARCHAR(20)
	DECLARE @nrRows int
	DECLARE @idTest int

	SET @nrRows = 1000
	SET @testInsert='Insert' + CONVERT(VARCHAR (5),@nrRows)
	SET @testDelete='Delete'++ CONVERT(VARCHAR (5),@nrRows)


	if @Table='Counties'
		BEGIN
			SET @t1 =GETDATE()
			exec @testInsert Counties
			exec @testDelete Counties
			SET @t2 =GETDATE()
			exec Eval Counties
			SET @t3 =GETDATE()
			SET @desc=N'The tests: '+@testInsert+', '+@testDelete+', and view on '+@Table
			INSERT INTO TestRuns VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
			INSERT INTO TestRunTables VALUES (@idTest,1,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,1,@t2,@t3)
		END
	if @Table='Boardings'
		BEGIN
			SET @t1 =GETDATE()
			exec @testInsert Boardings
			exec @testDelete Boardings
			SET @t2 =GETDATE()
			exec Eval Boardings
			SET @t3 =GETDATE()
			SET @desc=N'The tests '+@testInsert+', '+@testDelete+', and view on '+@Table
			INSERT INTO TestRuns VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
			INSERT INTO TestRunTables VALUES (@idTest,3,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,3,@t2,@t3)
		END
	if @Table='Passengers'
		BEGIN
			SET @t1 =GETDATE()
			exec @testInsert Passengers
			exec @testDelete Passengers
			SET @t2 =GETDATE()
			exec Eval Passengers
			SET @t3 =GETDATE()
			SET @desc=N'The tests '+@testInsert+', '+@testDelete+', and view on '+@Table
			INSERT INTO TestRuns VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
			INSERT INTO TestRunTables VALUES (@idTest,2,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,2,@t2,@t3)
		END
	--ELSE PRINT 'Invalid Table'
END

CREATE OR ALTER PROCEDURE main1 AS
    BEGIN
        DECLARE @i INT
        SELECT @i = COUNT(*) FROM Tables
        DECLARE @tableName varchar(10)

        DECLARE cursorT CURSOR SCROLL FOR
	    SELECT name FROM Tables;

        OPEN cursorT;
	    FETCH LAST FROM cursorT INTO @tableName

        WHILE @i>0 AND @@FETCH_STATUS=0
        BEGIN
            EXEC main @tableName
            print @tableName
            FETCH PRIOR FROM cursorT INTO @tableName;
            SET @i=@i-1
        END

        CLOSE cursorT;
        DEALLOCATE cursorT;
    END

EXEC main1


select * from TestRuns
select * from TestRunTables
select * from TestRunViews


DELETE TestRuns
DELETE TestRunTables
DELETE TestRunViews

--- Testings
DROP TABLE TestRunViews
DROP TABLE TestRunTables
DROP TABLE TestRuns
DROP TABLE TestTables
DROP TABLE TestViews
DROP TABLE Tests
DROP TABLE Tables
DROP TABLE Views