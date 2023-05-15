using model;
using persistance;
using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace server
{
    public class ProjectServicesImplementation: IProjectServices
    {
        public IAgencyRepository AgencyRepository { get; set; }
        public IBookingRepository BookingRepository { get; set; }
        public IClientRepository ClientRepository { get; set; }
        public IFlightRepository FlightRepository { get; set; }
        private readonly IDictionary<String, IProjectObserver> loggedClients;

        public ProjectServicesImplementation(IAgencyRepository agencyRepository, IBookingRepository bookingRepository, IClientRepository clientRepository, IFlightRepository flightRepository)
        {
            AgencyRepository = agencyRepository;
            BookingRepository = bookingRepository;
            ClientRepository = clientRepository;
            FlightRepository = flightRepository;
            loggedClients = new Dictionary<String, IProjectObserver>();
        }

        public void login(Agency agency, IProjectObserver client)
        {
            bool validUser = AgencyRepository.existsUser(agency.Id, agency.Password);
            
            if (validUser)
            {
                if (loggedClients.ContainsKey(agency.Id))
                {
                    throw new ProjectException("User already logged in");
                }
                loggedClients[agency.Id] = client; 
            } else
            {
                throw new ProjectException("Authentication failed");
            }
        }

        public void logout(Agency agency, IProjectObserver client)
        {
            IProjectObserver localClient = loggedClients[agency.Id];
            if (localClient == null)
                throw new ProjectException("User " + agency.Id + " is not logged in.");
            loggedClients.Remove(agency.Id);
        }

        public Flight[] getAllFlights(Agency agency, IProjectObserver client)
        {
            try
            {
                List<Flight> flights;
                flights = (List<Flight>)FlightRepository.findAll();

                return flights.ToArray();
            }
            catch (Exception e)
            {
                throw new ProjectException("Error " + e);
            }
        }

        public Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client)
        {
            try
            {
                List<Flight> flights;
                flights = (List<Flight>)FlightRepository.findFlightByDestinationAndDate(flight.Destination, flight.DepartureDateTime);
                
                return flights.ToArray();
            }
            catch (Exception e)
            {
                throw new ProjectException("Error " + e);
            }
        }

        public int getNumberOfSeats(Flight flight, IProjectObserver client)
        {
            try
            {
                return BookingRepository.getNumberOfBookingsForFlight(flight.Id);
            }
            catch (Exception e)
            {
                throw new ProjectException("Error " + e);
            }
        }

        public void bookFlight(Booking booking, IProjectObserver client)
        {
            try
            {
                booking.Client.Id = ClientRepository.getLowestAvailableId() + 1;
                ClientRepository.save(booking.Client);
                booking.Id = BookingRepository.getLowestAvailableId() + 1;
                BookingRepository.save(booking);
                notifyAgencies();
            }
            catch (Exception e)
            {
                throw new ProjectException("Error " + e);
            }
        }

        private void notifyAgencies()
        {
            foreach (var client in loggedClients.Values)
            {
                try
                {
                    client.notifyAgencies();
                }
                catch (ProjectException e)
                {
                    Console.WriteLine("Error notifying agency " + e);
                }
            }
        }
    }
}
