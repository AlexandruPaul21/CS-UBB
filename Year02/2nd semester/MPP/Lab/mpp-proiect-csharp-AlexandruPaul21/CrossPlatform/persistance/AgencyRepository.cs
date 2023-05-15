using System.Configuration;
using model;
using Npgsql;
// ReSharper disable All

namespace persistance;

public class AgencyRepository : IAgencyRepository
{

    public Agency findOne(string Id)
    {
        Agency agency = new Agency("", "");
        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();

            var sql = "SElECT * FROM agencies WHERE username = @username";
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("username", Id);
                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    string username = reader.GetString(0);
                    string name = reader.GetString(1);
                    string password = reader.GetString(2);

                    agency = new Agency(name, password);
                    agency.Id = username;
                }
            }
        }
        return agency;
    }

    public IEnumerable<Agency> findAll()
    {
        List<Agency> agencies = new List<Agency>();
        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();

            var sql = "SElECT * FROM agencies";
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    string username = reader.GetString(0);
                    string name = reader.GetString(1);
                    string password = reader.GetString(2);

                    Agency agency = new Agency(name, password);
                    agency.Id = username;

                    agencies.Add(agency);
                }
            }
        }
        return agencies;
    }

    public Agency save(Agency entity)
    {
        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();

            var sql = "INSERT INTO agencies VALUES (@username, @name, @password)";
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@username", entity.Id);
                cmd.Parameters.AddWithValue("@name", entity.Name);
                cmd.Parameters.AddWithValue("@password", entity.Password);

                int rowsAff = cmd.ExecuteNonQuery();
                if (rowsAff != 0)
                {
                    entity = null!;
                }
            }
        }
        return entity;
    }

    public bool existsUser(string username, string password)
    {
        bool exists = false;
        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();

            var sql = "SELECT * FROM agencies WHERE username = @username AND pass = @password";
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@username", username);
                cmd.Parameters.AddWithValue("@password", password);

                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    exists = true;
                }
            }
        }
        return exists;
    }
}