using System.Configuration;
using CsharpProj.model;
using log4net;
using Npgsql;
// ReSharper disable All

namespace CsharpProj.repository;

public class AgencyRepository : IAgencyRepository
{
    //private readonly Logger logger = new Logger();
    
    private static readonly ILog logger = LogManager.GetLogger("AgencyRepository");

    public Agency findOne(string Id)
    {
        logger.Info("Agency - findOne - Enter");
        Agency agency = new Agency("","");
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
        logger.Info("Agency - findOne - Exit");
        return agency;
    }

    public IEnumerable<Agency> findAll()
    {
        logger.Info("Agency - findAll - Enter");
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
        logger.Info("Agency - findAll - Exit");
        return agencies;
    }

    public Agency save(Agency entity)
    {
        logger.Info("Agency - save - Enter");
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
        logger.Info("Agency - save - Exit");
        return entity;
    }

    public bool existsUser(string username, string password)
    {
        logger.Info("Agency - existsUser - Enter");
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
        logger.Info("Agency - existsUser - Exit");
        return exists;
    }
}