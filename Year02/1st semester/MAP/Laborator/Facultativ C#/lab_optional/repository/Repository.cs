namespace lab_optional.repository;

public interface Repository<ID, E>
{
    List<E> findAll();
    E findOne(ID id);
}