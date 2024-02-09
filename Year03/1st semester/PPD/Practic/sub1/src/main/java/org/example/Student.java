package org.example;

public class Student {
    public Long id;
    public Float mean;

    public Student(Long id, Float mean) {
        this.id = id;
        this.mean = mean;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", mean=" + mean +
                '}';
    }
}
