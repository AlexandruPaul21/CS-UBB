package domain.validators;

/**
 * Interface for validating a type
 * @param <T> the type to be validated
 */
public interface Validator<T> {

    /**
     * Validates an entity
     * @param entity the entity to be validated
     * @throws ValidationException if the entity is not valid
     */
    public void validate(T entity) throws ValidationException;
}
