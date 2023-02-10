package com.example.simulation4.domain.DTOs;

import com.example.simulation4.domain.Pacient;

public class ChildDTO {
    private String cnp;
    private String varsta;
    private String prematur;
    private String diagnostic;
    private String gravitate;

    public ChildDTO(Pacient child) {
        cnp = String.valueOf(child.getCnp());
        varsta = String.valueOf(child.getVarsta());
        if (child.isPrematur()) {
            prematur = "DA";
        } else {
            prematur = "NU";
        }
        diagnostic = child.getDiagnosticPrincipal();
        gravitate = String.valueOf(child.getGravitate());
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getVarsta() {
        return varsta;
    }

    public void setVarsta(String varsta) {
        this.varsta = varsta;
    }

    public String getPrematur() {
        return prematur;
    }

    public void setPrematur(String prematur) {
        this.prematur = prematur;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getGravitate() {
        return gravitate;
    }

    public void setGravitate(String gravitate) {
        this.gravitate = gravitate;
    }
}
