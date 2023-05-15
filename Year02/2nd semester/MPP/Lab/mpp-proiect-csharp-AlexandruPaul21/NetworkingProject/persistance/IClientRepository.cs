using model;

namespace persistance;

public interface IClientRepository : CrudRepository<long, Client>
{
    long getLowestAvailableId();
}