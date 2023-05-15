package org.example.model;

import javax.persistence.Table;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "agencies_orm")
public class Agency extends Entity<String> {

    private String name;
    private String password;

    public Agency() {
    }

    public Agency(String username, String name, String password) {
        this.setId(username);
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agency agency)) return false;
        return Objects.equals(name, agency.name) && Objects.equals(password, agency.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
