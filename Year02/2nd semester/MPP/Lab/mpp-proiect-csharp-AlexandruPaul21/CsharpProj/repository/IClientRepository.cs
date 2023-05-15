using CsharpProj.model;

namespace CsharpProj.repository;

public interface IClientRepository : CrudRepository<long, Client>
{
    long getLowestAvailableId();
}