package repository.memory;

import domain.Friendship;
import domain.User;
import domain.validators.ArgumentException;
import domain.validators.Validator;
import repository.Repository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MemoryFriendshipRepository extends MemoryRepository<Long, Friendship> {
    public MemoryFriendshipRepository(Validator<Friendship> validator) {
        super(validator);
    }

    public boolean areFriends(Long id1, Long id2) {
        ArrayList<Long> friends = getFriendships(id1);
        return friends.contains(id2);
    }

    public ArrayList<Long> getFriendships(Long idFr){
        ArrayList<Long> users = new ArrayList<>();

        for (Map.Entry<Long, Friendship> entry : entities.entrySet()) {
            Friendship friendship = entry.getValue();

            if (friendship.getId1().equals(idFr)) {
                users.add(friendship.getId2());
            } else if(friendship.getId2().equals(idFr)) {
                users.add(friendship.getId1());
            }

        }

        return users;
    }

    public Long getLowestFreeId() {
        for (Long i = 1L; ; ++i) {
            if (!entities.containsKey(i)) {
                return i;
            }
        }
    }

    public Long delete(Long id1, Long id2) {
        for (Friendship friendship : entities.values()) {
            if (Long.min(friendship.getId1(), friendship.getId2()) == Long.min(id1, id2) &&
                Long.max(friendship.getId1(), friendship.getId2()) == Long.max(id1,id2)) {
                entities.remove(friendship.getId());
                return friendship.getId();
            }
        }
        throw new ArgumentException("The friendship does not exists");
    }
}
