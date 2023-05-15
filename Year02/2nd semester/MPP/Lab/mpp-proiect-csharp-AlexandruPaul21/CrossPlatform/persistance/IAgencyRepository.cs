using model;

namespace persistance;

public interface IAgencyRepository : CrudRepository<string, Agency>
{
    bool existsUser(string username, string password);
}