using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    public interface Response
    {
    }

    [Serializable]
    public class OkResponse : Response
    {

    }

    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;

        public ErrorResponse(string message)
        {
            this.message = message;
        }

        public virtual string Message
        {
            get
            {
                return message;
            }
        }
    }

    [Serializable]
    public class FlightsResponse : Response
    {
        public FlightDto[] flights { get; set; }
        public FlightsResponse(FlightDto[] flights)
        {
            this.flights = flights;
        }
    }

    [Serializable]
    public class SeatsResponse : Response
    {
        public int number { get; set; }

        public SeatsResponse(int number)
        {
            this.number = number;
        }
    }

    [Serializable]
    public class BookingUpdate : Response { }
}
