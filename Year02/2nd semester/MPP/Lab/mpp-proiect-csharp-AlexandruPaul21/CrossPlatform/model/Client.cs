namespace model;

[Serializable]
public class Client : Entity<long>
{
    public string Name { get; set; }
    public string Address { get; set; }

    public Client(string name, string address)
    {
        Name = name;
        Address = address;
    }
}