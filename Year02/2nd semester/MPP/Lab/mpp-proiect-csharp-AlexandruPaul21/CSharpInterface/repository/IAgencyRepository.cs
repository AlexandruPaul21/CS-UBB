using CSharpInterface.model;

namespace CSharpInterface.repository;

public interface IAgencyRepository : CrudRepository<string, Agency>
{
    bool existsUser(string username, string password);
}