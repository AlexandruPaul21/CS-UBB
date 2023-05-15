using CSharpInterface.model;

namespace CSharpInterface.repository;

public interface IClientRepository : CrudRepository<long, Client>
{
    long getLowestAvailableId();
}