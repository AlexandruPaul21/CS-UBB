using CSharpInterface.model;

namespace CSharpInterface.repository;

public interface IFlightRepository : CrudRepository<long, Flight>
{
    IEnumerable<Flight> findFlightByDestinationAndDate(string destination, DateTime dateTime);
}