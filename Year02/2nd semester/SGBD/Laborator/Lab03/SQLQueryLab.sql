CREATE TABLE log_table 
(
	id INT PRIMARY KEY IDENTITY,
	operation_type VARCHAR(20),
	table_name VARCHAR(20),
	execution_time DATETIME,
)
GO

CREATE FUNCTION validate_parameters_passangers(@name VARCHAR(100), @birthdate DATETIME, @gender VARCHAR(20), @id_address INT)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error VARCHAR(200)
	SET @error = ''

	IF (@name = '')
		SET @error = @error + 'Name cannot be void.'

	IF (@birthdate > GETDATE()) 
		SET @error = @error + 'The birthdate cannot be in future.'

	IF (LOWER(@gender) <> 'male' AND LOWER(@gender) <> 'female')
		SET @error = @error + 'Gender not valid.'

	IF (NOT(EXISTS(SELECT * FROM Streets WHERE id = @id_address)))
		SET @error = @error + 'Address id does not exist'

	RETURN @error
END
GO
DROP FUNCTION validate_parameters_passangers

CREATE FUNCTION validate_parameters_flight(@id_route INT, @id_team INT, @fl_date DATETIME, @price MONEY)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error VARCHAR(200)
	SET @error = ''

	IF (NOT(EXISTS(SELECT * FROM dbo.Routes WHERE id_route=@id_route)))
		SET @error = @error + 'Route id is not valid.'

	IF (NOT(EXISTS(SELECT * FROM dbo.Team WHERE id_team=@id_team)))
		SET @error = @error + 'Team id is not valid.'

	IF (@price < 0)
		SET @error = @error + 'Price cannot be negative.'

	RETURN @error
END
GO
DROP FUNCTION dbo.validate_parameters_flight

CREATE FUNCTION validate_parameters_borading(@id_flight INT, @id_pas INT)
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @error_msg VARCHAR(200)
	SET @error_msg = ''

	IF (EXISTS(SELECT * FROM Boardings WHERE id_flight = @id_flight AND id_pas = @id_pas))
		SET @error_msg = 'The entry already exists.'

	RETURN @error_msg
END
GO
DROP FUNCTION validate_parameters_borading

CREATE PROCEDURE insert_into_tables(@name VARCHAR(100), @birthdate DATETIME, @gender VARCHAR(20), @id_address INT, @id_route INT, @id_team INT, @fl_date DATETIME, @price MONEY)
AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		DECLARE @error_msg VARCHAR(200)
		SET @error_msg = dbo.validate_parameters_passangers(@name, @birthdate, @gender, @id_address)
		IF (@error_msg <> '')
		BEGIN
			PRINT @error_msg
			RAISERROR(@error_msg, 14, 1)
		END
		
		DECLARE @gender_bit BIT
		IF (LOWER(@gender) = 'male')
			SET @gender_bit = 1
		ELSE 
			SET @gender_bit = 0

		INSERT INTO Passengers VALUES (@name, @birthdate, @gender_bit, @id_address)
		INSERT INTO log_table VALUES ('INSERT', 'Passangers', CURRENT_TIMESTAMP)

		SET @error_msg = dbo.validate_parameters_flight(@id_route, @id_team, @fl_date, @price)
		IF (@error_msg <> '')
		BEGIN
			PRINT @error_msg
			RAISERROR(@error_msg, 14, 1)
		END

		INSERT INTO Flights VALUES (@id_route, @id_team, @fl_date, @price)
		INSERT INTO log_table VALUES ('INSERT', 'Flights', CURRENT_TIMESTAMP)

		DECLARE @id_pas INT, @id_flight INT
		SET @id_pas = (SELECT MAX(id_pas) FROM Passengers)
		INSERT INTO log_table VALUES ('SELECT', 'Passangers', CURRENT_TIMESTAMP)

		SET @id_flight = (SELECT MAX(id_flight) FROM Flights)
		INSERT INTO log_table VALUES ('SELECT', 'Flights', CURRENT_TIMESTAMP)

		SET @error_msg = dbo.validate_parameters_borading(@id_flight, @id_pas)
		IF (@error_msg <> '')
		BEGIN
			PRINT @error_msg
			RAISERROR(@error_msg, 14, 1)
		END

		INSERT INTO Boardings VALUES (@id_flight, @id_pas)
		INSERT INTO log_table VALUES ('INSERT', 'Boardings', CURRENT_TIMESTAMP)

		COMMIT TRAN
		PRINT 'Transaction commited'
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK TRAN
		PRINT 'Transaction rollbacked'
	END CATCH
END
GO
DROP PROCEDURE dbo.insert_into_tables

-- with success
EXEC insert_into_tables 'Andrei', '2002-04-15', 'female', 2, 2, 4, '2023-05-15', 100
SELECT * FROM log_table
SELECT * FROM Passengers
SELECT * FROM Flights
SELECT * FROM Boardings

-- without success
EXEC insert_into_tables '', '2002-04-20', 'female', 2, 1, 2, '2022-05-15', 1005
EXEC insert_into_tables 'Andrei', '2042-04-20', 'female', 2, 1, 2, '2022-05-15', 1005
EXEC insert_into_tables 'Andrei', '2002-04-20', 'dog', 2, 1, 2, '2022-05-15', 1005
EXEC insert_into_tables 'Andrei', '2002-04-20', 'female', 200, 1, 2, '2022-05-15', 1005
EXEC insert_into_tables 'Andrei', '2002-04-20', 'female', 2, 100, 2, '2022-05-15', 1005
EXEC insert_into_tables 'Andrei', '2002-04-20', 'female', 2, 1, 2, '2022-05-15', -5
SELECT * FROM log_table
SELECT * FROM Passengers
SELECT * FROM Flights
SELECT * FROM Boardings


-- task 02
CREATE PROCEDURE insert_into_tables2(@name VARCHAR(100), @birthdate DATETIME, @gender VARCHAR(20), @id_address INT, @id_route INT, @id_team INT, @fl_date DATETIME, @price MONEY)
AS
BEGIN
	DECLARE @err BIT
	SET @err = 0

	BEGIN TRAN
	BEGIN TRY
		DECLARE @error_msg VARCHAR(200)
		SET @error_msg = dbo.validate_parameters_passangers(@name, @birthdate, @gender, @id_address)
		IF (@error_msg <> '')
		BEGIN
			PRINT @error_msg
			RAISERROR(@error_msg, 14, 1)
		END
		
		DECLARE @gender_bit BIT
		IF (LOWER(@gender) = 'male')
			SET @gender_bit = 1
		ELSE 
			SET @gender_bit = 0

		INSERT INTO Passengers VALUES (@name, @birthdate, @gender_bit, @id_address)
		INSERT INTO log_table VALUES ('INSERT', 'Passangers', CURRENT_TIMESTAMP)

		COMMIT TRAN
		PRINT 'Transaction commited'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		INSERT log_table VALUES ('INSERT ERROR', 'Passangers', CURRENT_TIMESTAMP)
		PRINT 'Transaction rollbacked'
		SET @err = 1
	END CATCH

	BEGIN TRAN
	BEGIN TRY
		SET @error_msg = dbo.validate_parameters_flight(@id_route, @id_team, @fl_date, @price)
		IF (@error_msg <> '')
		BEGIN
			PRINT @error_msg
			RAISERROR(@error_msg, 14, 1)
		END

		INSERT INTO Flights VALUES (@id_route, @id_team, @fl_date, @price)
		INSERT INTO log_table VALUES ('INSERT', 'Flights', CURRENT_TIMESTAMP)

		COMMIT TRAN
		PRINT 'Transaction commited'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		INSERT log_table VALUES ('INSERT ERROR', 'Flights', CURRENT_TIMESTAMP)
		PRINT 'Transaction rollbacked'
		SET @err = 1
	END CATCH

	IF (@err = 1)
		return

	BEGIN TRAN
	BEGIN TRY
		DECLARE @id_pas INT, @id_flight INT
		SET @id_pas = (SELECT MAX(id_pas) FROM Passengers)
		INSERT INTO log_table VALUES ('SELECT', 'Passangers', CURRENT_TIMESTAMP)

		SET @id_flight = (SELECT MAX(id_flight) FROM Flights)
		INSERT INTO log_table VALUES ('SELECT', 'Flights', CURRENT_TIMESTAMP)

		SET @error_msg = dbo.validate_parameters_borading(@id_flight, @id_pas)
		IF (@error_msg <> '')
		BEGIN
			PRINT @error_msg
			RAISERROR(@error_msg, 14, 1)
		END

		INSERT INTO Boardings VALUES (@id_flight, @id_pas)
		INSERT INTO log_table VALUES ('INSERT', 'Boardings', CURRENT_TIMESTAMP)

		COMMIT TRAN
		PRINT 'Transaction commited'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		INSERT log_table VALUES ('INSERT ERROR', 'Boradings', CURRENT_TIMESTAMP)
		PRINT 'Transaction rollbacked'
	END CATCH
END
GO
DROP PROCEDURE dbo.insert_into_tables2

-- with success
EXEC insert_into_tables2 'Adrian', '2002-04-20', 'male', 2, 1, 2, '2022-05-15', 1005
SELECT * FROM log_table
SELECT * FROM Passengers
SELECT * FROM Flights
SELECT * FROM Boardings

-- without success
EXEC insert_into_tables2 '', '2002-04-20', 'femeale', 2, 1, 2, '2022-05-15', 1005 -- first not good
EXEC insert_into_tables2 'Adrian', '2042-04-20', 'female', 2, 1, 200, '2022-05-15', 1005 -- both not good
EXEC insert_into_tables2 'Adrian', '2002-04-20', 'dog', 2, 1, 2, '2022-05-15', 1005 -- first not good
EXEC insert_into_tables2 'Adrian', '2002-04-20', 'female', 200, 1, 2, '2022-05-15', 1005 -- first not good
EXEC insert_into_tables2 'Adrian', '2002-04-20', 'female', 2, 100, 2, '2022-05-15', 1005 -- second not good
EXEC insert_into_tables2 'Adrian', '2002-04-20', 'female', 2, 100, 2, '2022-05-15', -5 -- second not good
SELECT * FROM log_table
SELECT * FROM Passengers
SELECT * FROM Flights
SELECT * FROM Boardings