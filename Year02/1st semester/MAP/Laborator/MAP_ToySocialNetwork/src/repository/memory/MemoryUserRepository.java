package repository.memory;

import domain.User;
import domain.validators.Validator;

/**
 * Implements the given interface
 */
public class MemoryUserRepository extends MemoryRepository<Long, User> {
    public MemoryUserRepository(Validator<User> validator) {
        super(validator);
    }

    public Long getLowestFreeId() {
        for (Long i = 1L; ; ++i) {
            if (!entities.containsKey(i)) {
                return i;
            }
        }
    }
}
