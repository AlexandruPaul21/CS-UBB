USE Airlines226
GO

CREATE OR ALTER FUNCTION validateAirplaneId
(
    @id INT
)
RETURNS BIT
AS
    BEGIN
        if not exists (SELECT * FROM Airplanes WHERE id_air = @id)
        begin
            return 0
        end
        return 1
    END
GO

CREATE OR ALTER FUNCTION validatePilotsId
(
    @id INT
)
RETURNS BIT
AS
    BEGIN
        if exists (SELECT * FROM Pilots WHERE id_pilot = @id)
        begin
            return 0
        end
        return 1
    END
GO

CREATE OR ALTER FUNCTION validateName
(
    @name VARCHAR(40)
)
RETURNS BIT
AS
    BEGIN
        if @name is null
            return 1
        if LTRIM(@name) = ''
            return 0
        if LEN(@name) < 3
            return 0

        DECLARE @len INT
        SET @len = LEN(@name)

        DECLARE @index INT
        SET @index = 1

        WHILE @index < @len
        BEGIN
            IF NOT SUBSTRING(@name, @index, 1) LIKE '%[\-a-zA-z]'
                return 0

            SET @index = @index + 1
        END

        return 1
    END
GO

CREATE OR ALTER FUNCTION validateNumber
(
    @number varchar(40)
)
RETURNS BIT
AS
    BEGIN
        if @number is null
            return 1
        if LTRIM(@number) = ''
            return 0

        DECLARE @len INT
        SET @len = LEN(@number)

        DECLARE @index INT
        SET @index = 1

        DECLARE @car char

        WHILE @index < @len
        BEGIN
            select @car = SUBSTRING(@number, @index, 1)
		    if ISNUMERIC(@car) != 1
		        return 0
            SET @index = @index + 1
        END

        return 1
    END
GO

CREATE OR ALTER FUNCTION validateDate
(
    @date varchar(40)
)
RETURNS BIT
AS
    BEGIN
        if @date is null
		    RETURN 1
        if LEN(@date) < 9
            RETURN 0
        if not SUBSTRING(@date,1,4) LIKE '%20[0-9][0-9]'
           RETURN 0
        if not SUBSTRING(@date,6,1) LIKE '%[0-1]'
           RETURN 0

        if SUBSTRING(@date,6,1) = '0'
        BEGIN
            if not SUBSTRING(@date,7,1) LIKE '%[0-9]'
            BEGIN
                RETURN 0
            END
        END
        ELSE
            if not SUBSTRING(@date,7,1) LIKE '%[0-2]'
            BEGIN
                RETURN 0
            END

        if not SUBSTRING(@date,9,1) LIKE '%[0-3]'
           RETURN 0

        if not SUBSTRING(@date,9,1) LIKE '3'
        begin
            if not SUBSTRING(@date,9,2) LIKE '%[0-2][0-9]'
                RETURN 0
        end
        else
        begin
            if not SUBSTRING(@date,10,1) LIKE '%[0-1]'
                return 0
        end
        RETURN 1

    END
