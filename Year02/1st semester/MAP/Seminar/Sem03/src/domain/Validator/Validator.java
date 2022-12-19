package domain.Validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
