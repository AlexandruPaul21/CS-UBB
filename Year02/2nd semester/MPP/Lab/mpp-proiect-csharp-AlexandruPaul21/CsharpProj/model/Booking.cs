namespace CsharpProj.model;

public class Booking : Entity<long>
{
    public Flight Flight { get; set; }
    public Client Client { get; set; }
    public List<string> Passengers { get; set; }

    public Booking(Flight flight, Client client, List<string> passengers)
    {
        Flight = flight;
        Client = client;
        this.Passengers = passengers;
    }
}