//
// Created by Alexandru-Paul Sirbu on 19.04.2022.
//

#ifndef SEMINAR05_COFFEE_H
#define SEMINAR05_COFFEE_H

#include "beverage.h"

class Coffee : public Beverage {
public:
    Coffee() : Beverage("coffee") {}

    double price() override {
        return Beverage::price() + 50;
    }
};


#endif //SEMINAR05_COFFEE_H
