package repository.mock;

import model.Identifiable;
import repository.Repository;
import repository.RepositoryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AbstractRepository <ID, T extends Identifiable<ID>> implements Repository<ID,T > {

    protected Map<ID,T> elem;

    public AbstractRepository(){
        elem= new HashMap<>();

    }
    public T add(T el){
        if(elem.containsKey(el.getID()))
        {
            throw new RepositoryException("Element already exists!!!"+el);

        }
        else
            elem.put(el.getID(),el);
        return el;
    }

    public void delete(T el){
        if(elem.containsKey(el.getID()))
            elem.remove(el.getID());

    }

    public void update(T el,ID id){
        if(elem.containsKey(id))
            elem.put(el.getID(),el);
        else
            throw new RepositoryException("Element doesn’t exist"+id);
    }



    public T findById( ID id){
        if(elem.containsKey(id))
        return elem.get(id);
        else
            throw new RepositoryException("Element doesn't exist"+id);
    }
    public Iterable<T> findAll() {
        return elem.values();
    }

    @Override
    public Collection<T> getAll() {
        return elem.values();
    }
}


