namespace CSharpInterface.model;

public class Flight : Entity<long>
{
    public string Destination { get; set; }
    public DateTime DepartureDateTime { get; set; }
    public string Airport { get; set; }
    public int AvailableSeats { get; set; }

    public Flight(string destination, DateTime departureDateTime, string airport, int availableSeats)
    {
        Destination = destination;
        DepartureDateTime = departureDateTime;
        Airport = airport;
        AvailableSeats = availableSeats;
    }
}