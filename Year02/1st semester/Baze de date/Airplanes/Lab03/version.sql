USE Airlines226
GO

CREATE OR ALTER PROCEDURE CreateTable
AS
BEGIN
    CREATE TABLE temp_tb(
        id INT PRIMARY KEY IDENTITY,
        col1 VARCHAR(50) NOT NULL
    );
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'CreateTable','CreateTableUndo');
END
GO

CREATE OR ALTER  PROCEDURE CreateTableUndo
AS
BEGIN
    DROP TABLE temp_tb;
END
GO

CREATE OR ALTER  PROCEDURE DeleteTable
AS
BEGIN
    DROP TABLE temp_tb;
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'DeleteTable','DeleteTableUndo');
END
GO

CREATE OR ALTER  PROCEDURE DeleteTableUndo
AS
BEGIN
    CREATE TABLE temp_tb(
        id INT PRIMARY KEY IDENTITY,
        col1 VARCHAR(50) NOT NULL
    );
END
GO

-------------------------------------

CREATE OR ALTER  PROCEDURE AddConstraint
AS
BEGIN
    ALTER TABLE Pilots
    ADD CONSTRAINT df_number DEFAULT 1 FOR gender
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'AddConstraint','AddConstraintUndo');
END
GO


CREATE OR ALTER  PROCEDURE AddConstraintUndo
AS
BEGIN
    ALTER TABLE Pilots
    DROP CONSTRAINT df_number;
END
GO

------------------------------------------------

CREATE OR ALTER  PROCEDURE ModifyCol
AS
BEGIN
    ALTER TABLE Streets
    ALTER COLUMN number bigint;
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'ModifyCol','ModifyColUndo');

END
GO

CREATE OR ALTER  PROCEDURE ModifyColUndo
AS
BEGIN
    ALTER TABLE Streets
    ALTER COLUMN number int;
END
GO

------------------------------------------

CREATE OR ALTER  PROCEDURE AddNewColumn
AS
BEGIN
    ALTER TABLE Streets
    ADD building char;
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'AddNewCol','AddNewColumnUndo');
END
GO

CREATE OR ALTER  PROCEDURE AddNewColumnUndo
AS
BEGIN
    ALTER TABLE Streets
    DROP COLUMN building;
END
GO

---------------------------------------------------


CREATE OR ALTER  PROCEDURE AddNewForeignKey
AS
BEGIN
    ALTER TABLE Airports
    ADD CONSTRAINT fk_sec
    FOREIGN KEY (secondary_entrance) REFERENCES Streets(id);
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'AddNewForeignKey','AddNewForeignKeyUndo');

END
GO

CREATE OR ALTER  PROCEDURE AddNewForeignKeyUndo
AS
BEGIN
    ALTER TABLE Airports
    DROP CONSTRAINT fk_sec;
END
GO

CREATE OR ALTER  PROCEDURE RemoveForeignKey
AS
BEGIN
    ALTER TABLE Airports
    DROP CONSTRAINT fk_sec;
    DECLARE @id AS INT;
    SELECT @id = MAX(V.ver) FROM Versions V;
    IF (@id is null)
        SET @id = 0;
    SET @id = @id + 1;
    INSERT INTO Versions(ver, applied_op, undo_op) VALUES (@id,'RemoveForeignKey','RemoveForeignKeyUndo');

END
GO

CREATE OR ALTER  PROCEDURE RemoveForeignKeyUndo
AS
BEGIN
    ALTER TABLE Airports
    ADD CONSTRAINT fk_sec
    FOREIGN KEY (secondary_entrance) REFERENCES Streets(id);
END
GO



CREATE OR ALTER  PROCEDURE GoVer @ver INT
AS
BEGIN
    DECLARE @act_ver AS INT;
    SELECT @act_ver = MAX(V.ver) FROM Versions V;
    IF (@act_ver <= @ver OR @act_ver is null)
    BEGIN
        RAISERROR('The function call is wrong. Check and try again',16,1);
    END

    WHILE (@act_ver > @ver)
    BEGIN
        DECLARE @method VARCHAR(50);
        SELECT @method = V.undo_op FROM Versions V WHERE V.ver = @act_ver;
        EXEC @method
        DELETE FROM Versions WHERE ver = @act_ver;
        SET @act_ver = @act_ver -1
    END
    PRINT 'Operation ended successfully'
END
GO

CREATE OR ALTER  PROCEDURE main @new_ver int
AS
BEGIN
    DECLARE @ver INT;
    SELECT @ver=act FROM Version;
    if (@new_ver > 5 OR @new_ver < 0 OR @ver = @new_ver)
    BEGIN
        RAISERROR ('The call is not valid',16,1);
    END

    DECLARE @step INT;
    if (@new_ver > @ver)
        SET @step = 1
    else
        SET @step = -1

    WHILE (@new_ver != @ver)
    BEGIN

        DECLARE @operation VARCHAR(50);
        if (@step > 0)
            SELECT @operation = do FROM Transitions WHERE ver = @ver + 1
        else
            SELECT @operation = undo FROM Transitions WHERE ver = @ver
        SET @ver = @ver + @step;
        EXEC @operation;
        PRINT 'Success';
        UPDATE Version SET act = @ver;
    end

END
GO

EXEC AddConstraintUndo;
EXEC AddNewColumnUndo;
EXEC AddNewForeignKeyUndo;
EXEC CreateTableUndo;
EXEC ModifyColUndo;

EXEC AddConstraint;
EXEC AddNewColumn;
EXEC AddNewForeignKey;
EXEC CreateTable;
EXEC ModifyCol;

EXEC main 5;












