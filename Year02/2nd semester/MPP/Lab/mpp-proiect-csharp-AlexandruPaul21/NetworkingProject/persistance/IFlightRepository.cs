using model;

namespace persistance;

public interface IFlightRepository : CrudRepository<long, Flight>
{
    IEnumerable<Flight> findFlightByDestinationAndDate(string destination, DateTime dateTime);
}