package com.example.simulation5.domain;

public class Medic extends Entity<Long> {
    private Long idSectie;
    private String nume;
    private int vechime;
    private boolean rezident;

    public Medic(Long idSectie, String nume, int vechime, boolean rezident) {
        this.idSectie = idSectie;
        this.nume = nume;
        this.vechime = vechime;
        this.rezident = rezident;
    }

    public Long getIdSectie() {
        return idSectie;
    }

    public void setIdSectie(Long idSectie) {
        this.idSectie = idSectie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVechime() {
        return vechime;
    }

    public void setVechime(int vechime) {
        this.vechime = vechime;
    }

    public boolean isRezident() {
        return rezident;
    }

    public void setRezident(boolean rezident) {
        this.rezident = rezident;
    }
}
