package com.example.simulation4.domain;

import com.example.simulation4.domain.enums.TipPat;

public class Pat extends Entity<Long> {
    private TipPat tip;
    private boolean ventilatie;
    private Long cnpPacient;

    public Pat(TipPat tip, boolean ventilatie, Long cnpPacient) {
        this.tip = tip;
        this.ventilatie = ventilatie;
        this.cnpPacient = cnpPacient;
    }

    public TipPat getTip() {
        return tip;
    }

    public void setTip(TipPat tip) {
        this.tip = tip;
    }

    public boolean isVentilatie() {
        return ventilatie;
    }

    public void setVentilatie(boolean ventilatie) {
        this.ventilatie = ventilatie;
    }

    public Long getCnpPacient() {
        return cnpPacient;
    }

    public void setCnpPacient(Long cnpPacient) {
        this.cnpPacient = cnpPacient;
    }
}
