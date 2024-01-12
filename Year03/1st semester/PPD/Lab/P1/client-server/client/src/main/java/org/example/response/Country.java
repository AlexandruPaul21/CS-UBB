package org.example.response;

import java.io.Serializable;

public class Country implements Serializable {
    String countryName;
    int score;

    public Country(String countryName, int score) {
        this.countryName = countryName;
        this.score = score;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return countryName + ": " + score;
    }
}
