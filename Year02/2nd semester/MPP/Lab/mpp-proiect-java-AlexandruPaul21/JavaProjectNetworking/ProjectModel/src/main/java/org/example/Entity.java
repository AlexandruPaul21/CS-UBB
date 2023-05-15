package org.example;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
public class Entity<ID extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234987612L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
