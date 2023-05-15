using CsharpProj.model;

namespace CsharpProj.repository;

public interface IFlightRepository : CrudRepository<long, Flight>
{
    IEnumerable<Flight> findFlightByDestinationAndDate(string destination, DateTime dateTime);
}