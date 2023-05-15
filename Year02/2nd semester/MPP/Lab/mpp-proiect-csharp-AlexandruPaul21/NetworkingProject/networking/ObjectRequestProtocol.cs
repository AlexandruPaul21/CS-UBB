using System;
using System.Collections.Generic;
using System.Diagnostics.Contracts;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    public interface Request { }

    [Serializable]
    public class LoginRequest : Request 
    {
        public AgencyDto agencyDto { get; set; }

        public LoginRequest(AgencyDto agencyDto)
        {
            this.agencyDto = agencyDto;
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        public AgencyDto agencyDto { get; set; }

        public LogoutRequest(AgencyDto agencyDto)
        {
            this.agencyDto = agencyDto;
        }
    }

    [Serializable]
    public class AllFlightsRequest : Request
    {
        public AgencyDto agency { get; set; }

        public AllFlightsRequest(AgencyDto agency)
        {
            this.agency = agency;
        }
    }

    [Serializable]
    public class FilteredFlightsRequest : Request
    {
        public FlightDto flight { get; set; }

        public FilteredFlightsRequest(FlightDto flight)
        {
            this.flight = flight;
        }   
    }

    [Serializable]
    public class NumberOfSeatsRequest : Request
    {
        public FlightDto flight { get; set; }

        public NumberOfSeatsRequest(FlightDto flight)
        {
            this.flight = flight;
        }
    }

    [Serializable]
    public class BookingRequest : Request
    {
        public BookingDto booking { get; set; }

        public BookingRequest(BookingDto booking)
        {
            this.booking = booking;
        }
    }

}
