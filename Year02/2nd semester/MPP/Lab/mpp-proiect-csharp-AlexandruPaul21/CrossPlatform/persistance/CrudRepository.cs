using model;

namespace persistance;

public interface CrudRepository<ID, E> where E : Entity<ID>
{
    E findOne(ID Id);
    IEnumerable<E> findAll();
    E save(E entity);
}