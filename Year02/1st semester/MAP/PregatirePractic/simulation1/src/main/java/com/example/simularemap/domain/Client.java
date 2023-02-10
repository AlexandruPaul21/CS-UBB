package com.example.simularemap.domain;

import com.example.simularemap.domain.enums.HobbyType;

public class Client extends Entity<Long> {
    private String name;
    private int fidelityGrade;
    private int age;
    private HobbyType hobby;

    public Client(String name, int fidelityGrade, int age, HobbyType hobby) {
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.age = age;
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFidelityGrade() {
        return fidelityGrade;
    }

    public void setFidelityGrade(int fidelityGrade) {
        this.fidelityGrade = fidelityGrade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public HobbyType getHobby() {
        return hobby;
    }

    public void setHobby(HobbyType hobby) {
        this.hobby = hobby;
    }
}
