package com.example.simulation4.domain;

public class Pacient extends Entity<String>{
    private Long cnp;
    private int varsta;
    private boolean prematur;
    private String diagnosticPrincipal;
    private int gravitate;

    public Pacient(Long cnp, int varsta, boolean prematur, String diagnosticPrincipal, int gravitate) {
        this.cnp = cnp;
        this.varsta = varsta;
        this.prematur = prematur;
        this.diagnosticPrincipal = diagnosticPrincipal;
        this.gravitate = gravitate;
    }

    public Long getCnp() {
        return cnp;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public boolean isPrematur() {
        return prematur;
    }

    public void setPrematur(boolean prematur) {
        this.prematur = prematur;
    }

    public String getDiagnosticPrincipal() {
        return diagnosticPrincipal;
    }

    public void setDiagnosticPrincipal(String diagnosticPrincipal) {
        this.diagnosticPrincipal = diagnosticPrincipal;
    }

    public int getGravitate() {
        return gravitate;
    }

    public void setGravitate(int gravitate) {
        this.gravitate = gravitate;
    }
}
