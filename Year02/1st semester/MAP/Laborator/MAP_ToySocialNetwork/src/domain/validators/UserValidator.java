package domain.validators;

import domain.User;

/**
 * Custom validator, which implements the Validator Interface
 */
public class UserValidator implements Validator<User>{
    /**
     * Implements the validation function for an user
     * @param entity the entity to be added
     * @throws ValidationException if the user is not valid
     */
    @Override
    public void validate(User entity) throws ValidationException {
        String err = "";
        if (entity.getFirstname().equals("")) {
            err += "Firstname can't be void\n";
        }

        if (entity.getLastname().equals("")) {
            err += "Lastname can't be void\n";
        }

        if (!err.equals("")) {
            throw new ValidationException(err);
        }
    }
}
