using Google.Protobuf.WellKnownTypes;
using Org.Example;
using Agency = model.Agency;
using Booking = model.Booking;
using Client = model.Client;

namespace networking;

public class ProtoUtils
{
    public static ProjectResponse createOkResponse()
    {
        ProjectResponse response = new ProjectResponse {Type = ProjectResponse.Types.Type.Ok};
        return response;
    }
    
    public static ProjectResponse createErrorResponse(string text)
    {
        ProjectResponse response = new ProjectResponse {Type = ProjectResponse.Types.Type.Error, Error = text};
        return response;
    }

    public static Flight fromFlight(model.Flight flight)
    {
        Flight flightDTO = new Flight
        {
            Id = flight.Id,
            Destination = flight.Destination,
            Departure = Timestamp.FromDateTime(flight.DepartureDateTime.ToUniversalTime()),
            Airport = flight.Airport,
            AvailableSeats = flight.AvailableSeats
        };

        return flightDTO;
    }
    
    public static model.Flight getFlight(Flight flight)
    {
        model.Flight flightDTO = new model.Flight
        (
            flight.Destination,
            flight.Departure.ToDateTime(),
            flight.Airport,
            flight.AvailableSeats
        );
        flightDTO.Id = flight.Id;

        return flightDTO;
    }
    
    public static ProjectResponse createGetFlightsResponse(model.Flight[] flights)
    {
        ProjectResponse response = new ProjectResponse {Type = ProjectResponse.Types.Type.Flights};
        foreach (var flight in flights)
        {
           response.Flights.Add(fromFlight(flight));
        }
        return response;
    }
    
    public static ProjectResponse createGetFilteredFlightsResponse(model.Flight[] flights)
    {
        ProjectResponse response = new ProjectResponse {Type = ProjectResponse.Types.Type.FilteredFlights};
        foreach (var flight in flights)
        {
            response.Flights.Add(fromFlight(flight));
        }
        return response;
    }

    public static Agency getAgency(ProjectRequest request)
    {
        Agency agency = new Agency(request.Agency.Name, request.Agency.Password);
        agency.Id = request.Agency.Username;

        return agency;
    }
    
    public static ProjectResponse createNumberOfSeatsResponse(int numberOfSeats)
    {
        ProjectResponse response = new ProjectResponse {Type = ProjectResponse.Types.Type.NumberOfSeats};
        response.Number = numberOfSeats;
        return response;
    }

    public static Booking getBooking(ProjectRequest request)
    {
        List<String> passengers = new List<String>();

        var size = request.Booking.Passengers.Count;

        for (var i = 0; i < size; ++i)
        {
            passengers.Add(request.Booking.Passengers[i]);
        }

        Booking booking = new Booking(
            getFlight(request.Booking.Flight),
            getClient(request.Booking.Client),
            passengers
        );

        return booking;
    }

    private static Client getClient(Org.Example.Client bookingClient)
    {
        Client client = new Client(bookingClient.Name, bookingClient.Address);
        client.Id = bookingClient.Id;

        return client;
    }

    public static ProjectResponse createUpdateResponse()
    {
        ProjectResponse response = new ProjectResponse {Type = ProjectResponse.Types.Type.Booking};
        return response;
    }
}