package com.example.simulation5.domain;

public class Sectie extends Entity<Long> {
    private String nume;
    private Long idSef;
    private double pretPerConsultatie;
    private int durata;

    public Sectie(String nume, Long idSef, double pretPerConsultatie, int durata) {
        this.nume = nume;
        this.idSef = idSef;
        this.pretPerConsultatie = pretPerConsultatie;
        this.durata = durata;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Long getIdSef() {
        return idSef;
    }

    public void setIdSef(Long idSef) {
        this.idSef = idSef;
    }

    public double getPretPerConsultatie() {
        return pretPerConsultatie;
    }

    public void setPretPerConsultatie(double pretPerConsultatie) {
        this.pretPerConsultatie = pretPerConsultatie;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
}
