using System.Configuration;
using model;
using Npgsql;

namespace persistance;

public class FlightRepository : IFlightRepository
{
    public Flight findOne(long Id)
    {
        Flight flight = null!;

        var sql = "SELECT * FROM flights WHERE id = @id";

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
                    var destination = reader.GetString(1);
                    var departureDateTime = reader.GetDateTime(2);
                    var airport = reader.GetString(3);
                    var availableSeats = reader.GetInt32(4);

                    flight = new Flight(destination, departureDateTime, airport, availableSeats);
                    flight.Id = id;
                }
            }
        }
        return flight;
    }

    public IEnumerable<Flight> findAll()
    {
        var flights = new List<Flight>();

        var sql = "SELECT * FROM flights";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    long id = reader.GetInt32(0);
                    var destination = reader.GetString(1);
                    var departureDateTime = reader.GetDateTime(2);
                    var airport = reader.GetString(3);
                    var availableSeats = reader.GetInt32(4);

                    Flight flight = new Flight(destination, departureDateTime, airport, availableSeats);
                    flight.Id = id;

                    flights.Add(flight);
                }
            }
        }
        return flights;
    }

    public Flight save(Flight entity)
    {
        var sql = "INSERT INTO flights VALUES (@id, @destination, @departure, @airport, @available)";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@id", entity.Id);
                cmd.Parameters.AddWithValue("@destination", entity.Destination);
                cmd.Parameters.AddWithValue("@departure", entity.DepartureDateTime);
                cmd.Parameters.AddWithValue("@airport", entity.Airport);
                cmd.Parameters.AddWithValue("@available", entity.AvailableSeats);

                var rowsAff = cmd.ExecuteNonQuery();

                if (rowsAff != 0)
                {
                    entity = null!;
                }
            }
        }
        return entity;
    }

    public IEnumerable<Flight> findFlightByDestinationAndDate(string destination, DateTime dateTime)
    {
        DateTime start = new DateTime(dateTime.Year, dateTime.Month, dateTime.Day, 0, 0, 0);
        DateTime end = new DateTime(dateTime.Year, dateTime.Month, dateTime.Day, 23, 59, 59);

        var flights = new List<Flight>();

        var sql = "SELECT * FROM flights WHERE destination = @destination AND departure_date_time BETWEEN @start AND @end";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@destination", destination);
                cmd.Parameters.AddWithValue("@start", start);
                cmd.Parameters.AddWithValue("@end", end);
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    long id = reader.GetInt32(0);
                    var dest = reader.GetString(1);
                    var departureDateTime = reader.GetDateTime(2);
                    var airport = reader.GetString(3);
                    var availableSeats = reader.GetInt32(4);

                    Flight flight = new Flight(dest, departureDateTime, airport, availableSeats);
                    flight.Id = id;

                    flights.Add(flight);
                }
            }
        }
        return flights;
    }
}