package org.example.request;

import java.io.Serializable;

public class Result implements Serializable {
    int id;
    int score;
    String country;

    public Result(int id, int score, String country) {
        this.id = id;
        this.score = score;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public int getScore() {
        return score;
    }
}
