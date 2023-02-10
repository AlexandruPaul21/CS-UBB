package com.example.simulation3.domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    static Long SerialVersionUID = 2131244353L;

    ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
