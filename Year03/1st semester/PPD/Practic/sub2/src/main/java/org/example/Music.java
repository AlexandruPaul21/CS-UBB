package org.example;

public class Music {
    String name;
    String author;
    String department;

    public Music(String name, String author, String department) {
        this.name = name;
        this.author = author;
        this.department = department;
    }

    @Override
    public String toString() {
        return name + "," + author + "," + department;
    }
}
