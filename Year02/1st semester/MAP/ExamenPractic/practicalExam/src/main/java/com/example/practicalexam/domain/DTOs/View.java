package com.example.practicalexam.domain.DTOs;

import com.example.practicalexam.domain.City;

public class View {
    private City city1;
    private City city2;
    public int app = 0;

    public View(City city1, City city2) {
        this.city1 = city1;
        this.city2 = city2;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }
}
