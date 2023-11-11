package org.example;

public class FipElement {
    private String atom;
    private int codAtom;
    private int codInTs;

    public FipElement(String atom, int codAtom, int codInTs) {
        this.atom = atom;
        this.codAtom = codAtom;
        this.codInTs = codInTs;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
    }

    public int getCodAtom() {
        return codAtom;
    }

    public void setCodAtom(int codAtom) {
        this.codAtom = codAtom;
    }

    public int getCodInTs() {
        return codInTs;
    }

    public void setCodInTs(int codInTs) {
        this.codInTs = codInTs;
    }

    @Override
    public String toString() {
        return "FipElement{" +
                "codAtom=" + codAtom +
                ", codInTs=" + codInTs +
                '}';
    }
}
