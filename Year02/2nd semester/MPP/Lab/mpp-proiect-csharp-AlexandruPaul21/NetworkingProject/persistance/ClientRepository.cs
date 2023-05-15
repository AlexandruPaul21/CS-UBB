using System.Configuration;
using model;
using Npgsql;

namespace persistance;

public class ClientRepository : IClientRepository
{
    public Client findOne(long Id)
    {
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
        return client;
    }

    public IEnumerable<Client> findAll()
    {
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
        return clients;
    }

    public Client save(Client entity)
    {
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
        return entity;
    }

    public long getLowestAvailableId()
    {
        long id = 0;
        var sql = "SELECT id FROM clients ORDER BY id DESC";

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
}