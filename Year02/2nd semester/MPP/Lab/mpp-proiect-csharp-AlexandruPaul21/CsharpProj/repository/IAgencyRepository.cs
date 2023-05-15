using CsharpProj.model;

namespace CsharpProj.repository;

public interface IAgencyRepository : CrudRepository<string, Agency>
{
    bool existsUser(string username, string password);
}