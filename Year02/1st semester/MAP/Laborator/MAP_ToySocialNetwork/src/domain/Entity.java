package domain;

import java.io.Serial;
import java.io.Serializable;

/**
 * Interface for a serializable entity
 * @param <ID> the type of the entity
 */
public class Entity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7221115341259248461L;
    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID new_id) {
        id = new_id;
    }
}
