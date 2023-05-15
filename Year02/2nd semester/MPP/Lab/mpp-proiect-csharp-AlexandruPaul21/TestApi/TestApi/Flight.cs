namespace model;

[Serializable]
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

    public string ToString()
    {
        return "Flight{" +
               "id=" + Id + 
                "destination='" + Destination + '\'' +
                ", departureDateTime=" + DepartureDateTime +
                ", airport='" + Airport + '\'' +
                ", availableSeats=" + AvailableSeats +
                '}';
    }
}