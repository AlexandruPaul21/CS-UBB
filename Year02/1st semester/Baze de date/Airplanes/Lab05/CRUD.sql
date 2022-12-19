USE Airlines226
GO

CREATE OR ALTER PROCEDURE crudAirplanes
(
    @op varchar(40),
    @id varchar(40),
    @model varchar(40),
    @capacity varchar(40),
    @registration varchar(40),
    @id_gar varchar(40)
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateNumber(@id) = 0
        begin
            SET @valid = 0
            print 'Id is not a number'
        end
        if dbo.validateNumber(@capacity) = 0
        begin
            SET @valid = 0
            print 'Capacity is not a number'
        end
        if dbo.validateName(@registration) = 0
        begin
            SET @valid = 0
            print 'Registration is not a string'
        end
        if not exists(SELECT * FROM Garages WHERE id_gar = @id_gar)
        begin
            SET @valid = 0
            print 'The garage id does not exist'
        end

        if @valid = 0
        BEGIN
            print 'Invalid data'
            return
        END

        if dbo.validateAirplaneId(cast(@id as int)) = 0 OR (@id is null AND @op = 'delete')
            begin
                print 'The id is not in the table'
                return
            end

        if @op = 'select'
        begin

            SELECT A.model, A.registration, A.capacity, G.nameG FROM Airplanes A
            INNER JOIN Garages G on G.id_gar = A.id_gar
            WHERE id_air = @id

            print 'Select done'
        end
        else if @op = 'update'
        begin
            UPDATE Airplanes
            SET model = @model, registration = @registration, capacity = @capacity, id_gar = cast(@id_gar as int)
            WHERE id_air = @id
            print 'Update done'
        end
        else if @op = 'delete'
        begin
            DELETE Airplanes
            WHERE id_air = @id
            print 'Delete done'
        end
        else if @op = 'insert'
        begin
            INSERT INTO Airplanes VALUES
            (@model, @capacity, @registration, cast(@id_gar as int))
            print 'Insert done'
        end
        else
            print 'Invalid command'
    END
GO

CREATE OR ALTER PROCEDURE crudFlights
(
    @op varchar(30),
    @id varchar(40),
    @id_route varchar(40),
    @id_team varchar(40),
    @fl_date varchar(40),
    @price varchar(40)
)
AS
    BEGIN
        DECLARE @valid bit
        SET @valid = 1

        if dbo.validateNumber(@id) = 0
        begin
            print 'Invalid flight id'
            SET @valid = 0
        end

        if dbo.validateNumber(@id_team) = 0
        begin
            print 'Invalid team id'
            SET @valid = 0
        end

        if dbo.validateNumber(@id_route) = 0
        begin
            print 'Invalid route id'
            SET @valid = 0
        end

        if dbo.validateNumber(@price) = 0
        begin
            print 'Invalid price'
            SET @valid = 0
        end

        if dbo.validateDate(@fl_date) = 0
        begin
            print 'Invalid flight date'
            SET @valid = 0
        end

        if @valid = 0
            return

        if not exists(SELECT * FROM Flights WHERE id_flight = @id) OR (@id is null AND @op = 'delete')
            begin
                print 'Invalid id'
                return
            end

        if @op = 'select'
        begin
            SELECT A.nameA,R2.duration,P.nameP, Flights.price FROM Flights
            INNER JOIN Routes R2 on R2.id_route = Flights.id_route
            INNER JOIN Airports A on A.id_air = R2.id_from
            INNER JOIN Team T on T.id_team = Flights.id_team
            INNER JOIN Pilots P on P.id_pilot = T.id_pilot
            WHERE id_flight = @id
            print 'Select done'
        end
        else if @op = 'update'
        begin
            if not exists(SELECT * FROM Routes WHERE id_route = @id_route)
            begin
                print 'Invalid route id'
                return
            end
            if not exists(SELECT * FROM Team WHERE id_team = @id_team)
            begin
                print 'Invalid team id'
                return
            end

            UPDATE Flights
            SET id_team = cast(@id_team as int), id_route = cast(@id_route as int), fl_date = cast(@fl_date as datetime), price = cast(@price as money)
            WHERE id_flight = @id

            print 'Update done'
        end
        else if @op = 'delete'
        begin
            DELETE FROM Flights
            WHERE id_flight = @id

            print 'Delete done'
        end
        else if @op = 'insert'
        begin
            if not exists(SELECT * FROM Routes WHERE id_route = @id_route)
            begin
                print 'Invalid route id'
                return
            end
            if not exists(SELECT * FROM Team WHERE id_team = @id_team)
            begin
                print 'Invalid team id'
                return
            end

            INSERT INTO Flights
            VALUES (cast(@id_team as int), cast(@id_route as int), cast(@fl_date as datetime), cast(@price as money))
            print 'Insert done'
        end
        else
            print 'Invalid command'

    END
GO

CREATE OR ALTER PROCEDURE crudBoardings
(
    @op varchar(40),
    @id_flight varchar(40),
    @id_pas varchar(40)
)
AS
    BEGIN
        DECLARE @valid BIT
        SET @valid = 1

        if dbo.validateNumber(@id_flight) = 0
        begin
            print 'The flight id should be a number'
            SET @valid = 0
        end

        if dbo.validateNumber(@id_pas) = 0
        begin
            print 'The passenger id should be a number'
            SET @valid = 0
        end

        if @valid = 0
            return

        if not exists(SELECT * FROM Flights WHERE id_flight = @id_flight)
        begin
            print 'Invalid flight id'
            return
        end

        if not exists(SELECT * FROM Passengers where id_pas = @id_pas)
        begin
            print 'Invalid passenger id'
            return
        end

        if @op = 'select'
        begin
            if exists(SELECT * FROM Boardings where id_pas = @id_pas AND id_flight = @id_flight)
                print 'The record exists in the table'
            else
                print 'The record does not exists in the table'
        end
        else if @op = 'delete'
        begin
            if exists(SELECT * FROM Boardings WHERE id_pas = @id_pas AND id_flight = @id_flight)
            begin
                DELETE FROM Boardings WHERE id_pas = @id_pas AND id_flight = @id_flight
                print 'Delete done'
            end
            else
                print 'The record does not exists in the table'
        end
        else if @op = 'insert'
        begin
            if exists(SELECT * FROM Boardings WHERE id_pas = @id_pas AND id_flight = @id_flight)
                print 'Record already in the table'
            else
            begin
                INSERT INTO Boardings VALUES (@id_flight, @id_pas)
                print 'Insert done'
            end
        end
        else
            print 'Invalid command'

    END


