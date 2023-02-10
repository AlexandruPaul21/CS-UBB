package com.example.simulation5.domain;

import java.util.Date;

public class Consultatie extends Entity<Long> {
    private Long idMedic;
    private Long cnpPacient;
    private String numePacient;
    private Date data;
    private int ora;

    public Consultatie(Long idMedic, Long cnpPacient, String numePacient, Date data, int ora) {
        this.idMedic = idMedic;
        this.cnpPacient = cnpPacient;
        this.numePacient = numePacient;
        this.data = data;
        this.ora = ora;
    }

    public Long getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(Long idMedic) {
        this.idMedic = idMedic;
    }

    public Long getCnpPacient() {
        return cnpPacient;
    }

    public void setCnpPacient(Long cnpPacient) {
        this.cnpPacient = cnpPacient;
    }

    public String getNumePacient() {
        return numePacient;
    }

    public void setNumePacient(String numePacient) {
        this.numePacient = numePacient;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }
}
