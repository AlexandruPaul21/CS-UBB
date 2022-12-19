package repository.memory;

import domain.Entity;
import domain.validators.ArgumentException;
import domain.validators.Validator;
import repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    protected Validator<E> validator;
    protected Map<ID, E> entities;

    /**
     * Basic constructor
     * @param validator : the validator for the entities
     */
    public MemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities =  new HashMap<ID, E>();
    }

    /**
     * Returns one element of the list
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the given id
     */
    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new ArgumentException("The ID that has to be find cannot be null");
        }
        if (entities.get(id) == null) {
            throw new ArgumentException("The id does not exist");
        }
        return entities.get(id);
    }

    /**
     *
     * @return all the values from the list
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * Save the entity to the repo
     * @param entity to be added
     *         entity must be not null
     * @return null - if the entity was added
     *          or the entity, if the entity already exists
     * @throws ArgumentException if the entity is null
     */
    @Override
    public E save(E entity) {
        if (entity == null) {
            throw new ArgumentException("We cannot add null to a list");
        }
        validator.validate(entity);
        if (entities.get(entity.getId()) != null) {
            return entity;
        }
        entities.put(entity.getId(),entity);
        return null;
    }

    /**
     * Deletes the object from the repo
     * @param id the id of the object to be deleted
     *      id must be not null
     * @return null if the entity does not exist
     *       or the deleted entity if the deletion was successful
     * @throws ArgumentException if the id is null
     */
    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new ArgumentException("The ID to be deleted cannot be null");
        }
        if (entities.get(id) == null) {
            return null;
        }
        E copyEnt = entities.get(id);
        entities.remove(id);
        return copyEnt;
    }

    /**
     * Updates an entity
     * @param entity the entity to be updated
     *          entity must not be null
     * @return null if the entity was updated
     *          or the entity otherwise
     * @throws ArgumentException if the entity is null
     */
    @Override
    public E update(E entity) {
        if (entity == null) {
            throw new ArgumentException("Entity to be updated cannot be null");
        }
        if (entities.get(entity.getId()) == null) {
            return entity;
        }
        entities.replace(entity.getId(), entity);
        return null;
    }
}
