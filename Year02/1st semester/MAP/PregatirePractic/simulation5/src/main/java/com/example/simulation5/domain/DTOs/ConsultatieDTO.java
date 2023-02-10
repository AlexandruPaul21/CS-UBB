package com.example.simulation5.domain.DTOs;

import com.example.simulation5.domain.Consultatie;

public class ConsultatieDTO {
    private Long id;
    private String sectie;
    private String numePac;
    private String dataOra;

    public ConsultatieDTO(Consultatie consultatie, String sectie) {
        id = consultatie.getId();
        this.sectie = sectie;
        numePac = consultatie.getNumePacient();
        dataOra = consultatie.getData().toString() + " " + consultatie.getOra();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public String getNumePac() {
        return numePac;
    }

    public void setNumePac(String numePac) {
        this.numePac = numePac;
    }

    public String getDataOra() {
        return dataOra;
    }

    public void setDataOra(String dataOra) {
        this.dataOra = dataOra;
    }
}
