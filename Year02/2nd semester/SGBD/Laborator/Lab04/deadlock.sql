USE Airlines226
GO

CREATE PROCEDURE deadlock_t1 AS
BEGIN 
	BEGIN TRAN
	BEGIN TRY
		UPDATE Flights SET price = 6969 WHERE id_flight = 1
		INSERT INTO log_table VALUES ('UPDATE', 'Flights', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Team SET id_aux = 6, id_pilot = 4 WHERE id_team = 1
		INSERT INTO log_table VALUES ('UPDATE', 'Team', CURRENT_TIMESTAMP)

		COMMIT TRAN
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		PRINT ERROR_MESSAGE()
	END CATCH
END
GO

CREATE PROCEDURE deadlock_t2 AS
BEGIN 
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
	-- SET DEADLOCK_PRIORITY HIGH
	SET DEADLOCK_PRIORITY LOW
	BEGIN TRAN
	BEGIN TRY
		UPDATE Team SET id_aux = 1, id_pilot = 4 WHERE id_team = 1
		INSERT INTO log_table VALUES ('UPDATE', 'Team', CURRENT_TIMESTAMP)

		WAITFOR DELAY '00:00:05'

		UPDATE Flights SET price = 10 WHERE id_flight = 1
		INSERT INTO log_table VALUES ('UPDATE', 'Flights', CURRENT_TIMESTAMP)

		COMMIT TRAN
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK TRAN
	END CATCH
END
GO

CREATE PROCEDURE deadlock_t1_c# AS
BEGIN 
	BEGIN TRAN
	UPDATE Flights SET price = 6969 WHERE id_flight = 1
	INSERT INTO log_table VALUES ('UPDATE', 'Flights', CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05'
	UPDATE Team SET id_aux = 6, id_pilot = 4 WHERE id_team = 1
	INSERT INTO log_table VALUES ('UPDATE', 'Team', CURRENT_TIMESTAMP)

	COMMIT TRAN
END
GO

CREATE PROCEDURE deadlock_t2_c# AS
BEGIN 
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
	-- SET DEADLOCK_PRIORITY HIGH
	SET DEADLOCK_PRIORITY LOW
	BEGIN TRAN
	
	UPDATE Team SET id_aux = 1, id_pilot = 4 WHERE id_team = 1
	INSERT INTO log_table VALUES ('UPDATE', 'Team', CURRENT_TIMESTAMP)

	WAITFOR DELAY '00:00:05'

	UPDATE Flights SET price = 10 WHERE id_flight = 1
	INSERT INTO log_table VALUES ('UPDATE', 'Flights', CURRENT_TIMESTAMP)

	COMMIT TRAN
END
GO

EXEC deadlock_t1
EXEC deadlock_t2

DROP PROCEDURE deadlock_t1
DROP PROCEDURE deadlock_t2