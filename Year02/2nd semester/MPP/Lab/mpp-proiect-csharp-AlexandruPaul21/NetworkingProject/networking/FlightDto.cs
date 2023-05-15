using model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    [Serializable]
    public class FlightDto
    {
        public long id { get; set; }
        public String destination { get; set; }
        public String departure { get; set; }
        public String airport { get; set; }
        public String avbSeats { get; set; }

        public FlightDto(Flight flight)
        {
            this.id = flight.Id;
            this.destination = flight.Destination;
            this.departure = flight.DepartureDateTime.ToString();
            this.airport = flight.Airport;
            this.avbSeats = flight.AvailableSeats.ToString();
        }
    }
}
