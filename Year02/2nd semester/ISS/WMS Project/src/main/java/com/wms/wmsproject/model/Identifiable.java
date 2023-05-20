package com.wms.wmsproject.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public class Identifiable<ID extends Serializable> {
    @Id
    ID id;

    public Identifiable() {
    }

    public Identifiable(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
