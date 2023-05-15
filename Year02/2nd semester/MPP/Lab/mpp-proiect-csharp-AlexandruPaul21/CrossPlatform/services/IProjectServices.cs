using model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace services
{
    public interface IProjectServices
    {
        void login(Agency agency, IProjectObserver client);
        void logout(Agency agency, IProjectObserver client);
        Flight[] getAllFlights(Agency agency, IProjectObserver client);
        Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client);
        int getNumberOfSeats(Flight flight, IProjectObserver client);
        void bookFlight(Booking booking, IProjectObserver client);
    }
}
