using CSharpInterface.model;

namespace CSharpInterface.repository;

public interface IBookingRepository : CrudRepository<long, Booking>
{
    long getLowestAvailableId();
}