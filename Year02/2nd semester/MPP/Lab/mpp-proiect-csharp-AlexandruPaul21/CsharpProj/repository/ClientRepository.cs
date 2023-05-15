using System.Configuration;
using CsharpProj.model;
using log4net;
using Npgsql;

namespace CsharpProj.repository;

public class ClientRepository : IClientRepository
{
    //private readonly Logger logger = new Logger();
    
    private static readonly ILog logger = LogManager.GetLogger("ClientRepository");
    
    public Client findOne(long Id)
    {
        logger.Info("Client - findOne - Enter");
        Client client = new Client("", "");

        var sql = "SELECT * FROM clients WHERE id = @id";

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
                    var name = reader.GetString(1);
                    var address = reader.GetString(2);

                    client = new Client(name, address);
                    client.Id = id;
                }
            }
        }
        logger.Info("Client - findOne - Exit");
        return client;
    }

    public IEnumerable<Client> findAll()
    {
        logger.Info("Client - findAll - Enter");
        List<Client> clients = new List<Client>();
        var sql = "SELECT * FROM clients";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    long id = reader.GetInt32(0);
                    var name = reader.GetString(1);
                    var address = reader.GetString(2);

                    Client client = new Client(name, address);
                    client.Id = id;
                    
                    clients.Add(client);
                }
            }
        }
        logger.Info("Client - findOne - Exit");
        return clients;
    }

    public Client save(Client entity)
    {
        logger.Info("Client - save - Enter");
        var sql = "INSERT INTO clients VALUES (@id, @client_name, @address)";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@id", entity.Id);
                cmd.Parameters.AddWithValue("@client_name", entity.Name);
                cmd.Parameters.AddWithValue("@address", entity.Address);

                int rowsAff = cmd.ExecuteNonQuery();

                if (rowsAff != 0)
                {
                    entity = null!;
                }
            }
        }
        logger.Info("Client - save - Exit");
        return entity;
    }

    public long getLowestAvailableId()
    {
        logger.Info("Client - getLowestAvailableId - Enter");
        long id = 0;
        var sql = "SELECT id FROM clients ORDER BY id";

        using (var connection = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var cmd = new NpgsqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    long id1 = reader.GetInt32(0);
                    if (id1 != id)
                    {
                        break;
                    }
                    id++;
                }
            }
        }
        logger.Info("Client - getLowestAvailableId - Exit");
        return id;
    }
}