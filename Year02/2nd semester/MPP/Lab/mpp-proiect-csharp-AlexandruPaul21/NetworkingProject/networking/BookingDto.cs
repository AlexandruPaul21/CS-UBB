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
    public class BookingDto
    {
        public long id { get; set; }
        public Flight flight { get; set; }
        public Client client { get; set; }
        public List<String> passengers { get; set; }

        public BookingDto(long id, Flight flight, Client client, List<String> passangers)
        {
            this.id = id;
            this.flight = flight;
            this.client = client;
            this.passengers = passangers;
        }
    }
}
