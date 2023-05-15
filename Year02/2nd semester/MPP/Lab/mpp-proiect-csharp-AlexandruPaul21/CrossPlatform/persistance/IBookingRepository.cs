using model;

namespace persistance;

public interface IBookingRepository : CrudRepository<long, Booking>
{
    long getLowestAvailableId();
    int getNumberOfBookingsForFlight(long id);
}