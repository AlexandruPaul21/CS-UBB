package com.example.simulation5.domain.DTOs;

import com.example.simulation5.domain.Medic;

public class MedicDTO {
    private Long id;
    private String nume;

    public MedicDTO(Medic medic) {
        id = medic.getId();
        nume = medic.getNume();
    }

    @Override
    public String toString() {
        return nume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
