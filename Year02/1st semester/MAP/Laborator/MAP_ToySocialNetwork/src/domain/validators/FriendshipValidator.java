package domain.validators;

import domain.Friendship;

import java.util.Objects;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (Objects.equals(entity.getId1(), entity.getId2())) {
            throw new ValidationException("The ids cannot be the same");
        }
    }
}
