using CsharpProj.model;
using CsharpProj.repository;
// ReSharper disable All

namespace CsharpProj.service;

public class Service
{
    public IAgencyRepository AgencyRepository {get; set;}
    public IBookingRepository BookingRepository { get; set; }
    public IClientRepository ClientRepository { get; set; }
    public IFlightRepository FlightRepository { get; set; }

    public Service(IAgencyRepository agencyRepository, IBookingRepository bookingRepository, IClientRepository clientRepository, IFlightRepository flightRepository)
    {
        AgencyRepository = agencyRepository;
        BookingRepository = bookingRepository;
        ClientRepository = clientRepository;
        FlightRepository = flightRepository;
    }
    
    public bool validateLogin(string username, string password)
    {
        return AgencyRepository.existsUser(username, password);
    }
    
    public IEnumerable<Flight> findFlightByDestinationAndDate(string destination, DateTime dateTime)
    {
        return FlightRepository.findFlightByDestinationAndDate(destination, dateTime);
    }
    
    public int getNumberOfBookingsForFlight(Flight flight)
    {
        return BookingRepository.getNumberOfBookingsForFlight(flight);
    }
    
    public IEnumerable<Flight> getAllFlights()
    {
        return FlightRepository.findAll();
    }

    public bool bookFlight(long flightId, string clientName, string clientAddress, List<string> clients)
    {
        Client client = new Client(clientName, clientAddress);
        client.Id = ClientRepository.getLowestAvailableId() + 1;
        ClientRepository.save(client);
        Booking booking = new Booking(FlightRepository.findOne(flightId), client, clients);
        booking.Id = BookingRepository.getLowestAvailableId() + 1;
        return BookingRepository.save(booking) != null;
    }

}