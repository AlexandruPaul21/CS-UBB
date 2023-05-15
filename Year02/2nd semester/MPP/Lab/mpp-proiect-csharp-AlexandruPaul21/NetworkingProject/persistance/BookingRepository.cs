using System.Configuration;
using model;
using Npgsql;

namespace persistance;

public class BookingRepository : IBookingRepository
{
    //private readonly Logger logger = new Logger();

    public Booking findOne(long Id)
    {
        Booking booking = null!;
        var sql = "SELECT * FROM bookings b " +
                  "INNER JOIN clients c on c.id = b.id_client " +
                  "INNER JOIN flights f on f.id = b.id_flight WHERE b.id = @id";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@id", Id);
                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    long id = reader.GetInt32(0);
                    var clientsString = reader.GetString(3);

                    long idClient = reader.GetInt32(4);
                    var nameClient = reader.GetString(5);
                    var addressClient = reader.GetString(6);

                    Client client = new Client(nameClient, addressClient);
                    client.Id = idClient;

                    long idFlight = reader.GetInt32(7);
                    var destination = reader.GetString(8);
                    var departureDateTime = reader.GetDateTime(9);
                    var airport = reader.GetString(10);
                    var availableSeats = reader.GetInt32(11);

                    Flight flight = new Flight(destination, departureDateTime, airport, availableSeats);
                    flight.Id = idFlight;

                    var clients = clientsString.Split(",");

                    booking = new Booking(flight, client, new List<string>(clients));
                    booking.Id = id;
                }
            }
        }
        return booking;
    }

    public IEnumerable<Booking> findAll()
    {
        var bookings = new List<Booking>();
        var sql = "SELECT * FROM bookings b " +
                  "INNER JOIN clients c on c.id = b.id_client " +
                  "INNER JOIN flights f on f.id = b.id_flight";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    long id = reader.GetInt32(0);
                    var clientsString = reader.GetString(3);

                    long idClient = reader.GetInt32(4);
                    var nameClient = reader.GetString(5);
                    var addressClient = reader.GetString(6);

                    Client client = new Client(nameClient, addressClient);
                    client.Id = idClient;

                    long idFlight = reader.GetInt32(7);
                    var destination = reader.GetString(8);
                    var departureDateTime = reader.GetDateTime(9);
                    var airport = reader.GetString(10);
                    var availableSeats = reader.GetInt32(11);

                    Flight flight = new Flight(destination, departureDateTime, airport, availableSeats);
                    flight.Id = idFlight;

                    var clients = clientsString.Split(",");

                    Booking booking = new Booking(flight, client, new List<string>(clients));
                    booking.Id = id;

                    bookings.Add(booking);
                }
            }
        }
        return bookings;
    }

    public Booking save(Booking entity)
    {
        var sql = "INSERT INTO bookings VALUES (@id, @idFlight, @idClient, @clients)";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@id", entity.Id);
                cmd.Parameters.AddWithValue("@idFlight", entity.Flight.Id);
                cmd.Parameters.AddWithValue("@idClient", entity.Client.Id);

                var clientsString = string.Join(",", entity.Passengers);
                cmd.Parameters.AddWithValue("@clients", clientsString);

                var rowsAff = cmd.ExecuteNonQuery();

                if (rowsAff != 0)
                {
                    entity = null!;
                }
            }
        }
        return entity;
    }

    public long getLowestAvailableId()
    {
        var sql = "SELECT id FROM bookings ORDER BY id DESC";
        long id = 0;
        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    id = reader.GetInt32(0);
                }
                else
                {
                    id = 1;
                }
            }
        }
        return id;
    }

    public int getNumberOfBookingsForFlight(long id)
    {
        var bookings = findAll();

        int count = 0;
        foreach (var book in bookings)
        {
            if (book.Flight.Id == id)
            {
                count += book.Passengers.Count + 1;
            }
        }

        return count;
    }
}