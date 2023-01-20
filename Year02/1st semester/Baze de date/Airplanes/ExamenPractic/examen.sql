USE Exam
GO

--4

CREATE TABLE Hala(
    id INT PRIMARY KEY IDENTITY(1,1),
    litera char not null,
    suprafata float not null
)
GO

CREATE TABLE Taraba(
    id INT PRIMARY KEY IDENTITY(1,1),
    suprafata float not null,
    numar int not null,
    idHala int FOREIGN KEY REFERENCES Hala(id)
)
GO

CREATE TABLE CategorieProduse(
    id INT PRIMARY KEY IDENTITY(1,1),
    nume varchar(20),
)
GO

CREATE TABLE TarabeCategorii(
    idTaraba INT FOREIGN KEY REFERENCES Taraba(id),
    idCategorie INT FOREIGN KEY REFERENCES CategorieProduse(id),
    CONSTRAINT pk_tarabeCat PRIMARY KEY(idTaraba, idCategorie)
)

CREATE TABLE Produs(
    id INT PRIMARY KEY IDENTITY(1,1),
    denumire varchar(30),
    pret float,
    idCat int FOREIGN KEY REFERENCES CategorieProduse(id)
)
GO

INSERT INTO Hala(litera, suprafata) VALUES ('H', 20.5)

INSERT INTO Taraba(suprafata, numar, idHala) VALUES (20, 10, 1)

INSERT INTO CategorieProduse(nume) VALUES ('Legume')

INSERT INTO Produs(denumire, pret, idCat) VALUES ('Rosii', 5, 1)

INSERT INTO TarabeCategorii(idTaraba, idCategorie) VALUES (1,1)

CREATE OR ALTER PROCEDURE ex2 @idTaraba INT AS
    BEGIN

        DECLARE @idP int, @price float

        --- cursor
        DECLARE cursorPass CURSOR SCROLL FOR
        SELECT P.id, P.pret FROM TarabeCategorii TC
        INNER JOIN CategorieProduse CP on CP.id = TC.idCategorie
        INNER JOIN Produs P on CP.id = P.idCat
        WHERE @idTaraba = TC.idTaraba;

        OPEN cursorPass;

        FETCH LAST FROM cursorPass INTO @idP, @price;

        DECLARE @i INT

        SELECT @i=COUNT(*) FROM TarabeCategorii TC
        INNER JOIN CategorieProduse CP on CP.id = TC.idCategorie
        INNER JOIN Produs P on CP.id = P.idCat
        WHERE @idTaraba = TC.idTaraba;

        WHILE @i>0 AND @@FETCH_STATUS=0
            BEGIN
                DECLARE @newPrice INT;
                if @price < 100
                    SET @newPrice = @price + 10
                if @price > 200
                    SET @newPrice = @price +50
                if @price >= 100 AND @price < 200
                    SET @newPrice = 1.1*@price

                UPDATE Produs SET pret = @newPrice WHERE id = @idP
                FETCH PRIOR FROM cursorPass INTO @idP, @price;
            SET @i=@i-1
        END

        CLOSE cursorPass;
        DEALLOCATE cursorPass;

    END
GO

EXEC ex2 1
GO

CREATE OR ALTER PROCEDURE ex22 @idTaraba INT AS
    BEGIN
        UPDATE Produs SET pret = pret + 10 WHERE idCat = ANY (SELECT idCat FROM TarabeCategorii WHERE idTaraba = @idTaraba) AND pret < 100
        UPDATE Produs SET pret = pret + 50 WHERE idCat = ANY (SELECT idCat FROM TarabeCategorii WHERE idTaraba = @idTaraba) AND pret > 200
        UPDATE Produs SET pret = pret *1.1 WHERE idCat = ANY (SELECT idCat FROM TarabeCategorii WHERE idTaraba = @idTaraba) AND pret > 99 AND pret < 201
    END

EXEC ex22 1

CREATE OR ALTER VIEW view3 AS
    SELECT P.denumire, P.pret*0.4 FROM TarabeCategorii TC
    INNER JOIN CategorieProduse CP on CP.id = TC.idCategorie
    INNER JOIN Taraba T on T.id = TC.idTaraba
    INNER JOIN Hala H on H.id = T.idHala
    INNER JOIN Produs P on CP.id = P.idCat
    WHERE (CP.nume = 'haine' OR CP.nume = 'vesela') AND (H.litera = 'A' OR H.litera = 'F' OR H.litera = 'X')
GO
