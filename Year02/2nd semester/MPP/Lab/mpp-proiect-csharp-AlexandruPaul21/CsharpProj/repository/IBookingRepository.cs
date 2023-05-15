using CsharpProj.model;

namespace CsharpProj.repository;

public interface IBookingRepository : CrudRepository<long, Booking>
{
    int getNumberOfBookingsForFlight(Flight flight);
    long getLowestAvailableId();
}