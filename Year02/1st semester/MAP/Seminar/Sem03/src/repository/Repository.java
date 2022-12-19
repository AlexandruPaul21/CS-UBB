package repository;

public interface Repository<E, ID> {
    E save(E entity);
    E delete(ID id);
    E findOne(ID id);
    Iterable<E> findAll();
}
