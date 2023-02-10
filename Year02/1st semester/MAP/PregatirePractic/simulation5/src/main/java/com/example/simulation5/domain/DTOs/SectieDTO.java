package com.example.simulation5.domain.DTOs;

import com.example.simulation5.domain.Sectie;

public class SectieDTO {
    private Long id;
    private String nume;
    private String pret;
    private String durata;

    public SectieDTO(Sectie sectie) {
        id = sectie.getId();
        nume = sectie.getNume();
        pret = String.valueOf(sectie.getPretPerConsultatie());
        durata = String.valueOf(sectie.getDurata());
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

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        this.pret = pret;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }
}
