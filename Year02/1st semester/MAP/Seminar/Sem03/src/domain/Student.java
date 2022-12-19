package domain;

import java.util.Objects;

public class Student extends Entity<Long>{
    private String nume;
    private float media;

    public String toString() {
        return nume + " " + media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //daca adresa este acceasi, obiectele sunt egale

        if (o == null || getClass() != o.getClass()) return false;
        //daca obiectul este nul, sau au clase diferite, ele nu pot fi egale

        Student student = (Student) o; //facem un cast din obiect la student
        return Float.compare(student.media, media) == 0 && nume.equals(student.nume);
        //daca atributele sunt egale, atunci si obiectele sunt egale
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, media);
    }

    public Student(String nume, float media) {
        this.nume = nume;
        this.media = media;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int getMediaRotunjita() {
        return Math.round(getMedia());
    }
}
