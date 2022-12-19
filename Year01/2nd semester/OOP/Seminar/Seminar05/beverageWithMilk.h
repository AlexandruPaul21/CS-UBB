//
// Created by Alexandru-Paul Sirbu on 19.04.2022.
//

#ifndef SEMINAR05_BEVERAGEWITHMILK_H
#define SEMINAR05_BEVERAGEWITHMILK_H

#include "beverage.h"

class BeverageWithMilk : public Beverage {
    int milkCount;
    Beverage* beverage;
public:
    BeverageWithMilk() = default;

    BeverageWithMilk(Beverage *b, int milkCount)
            : beverage(b),
              milkCount{milkCount},
              Beverage("with milk") {
    };

    double price() override {
        return beverage->price() + this->milkCount * 50;
    }
    void print(){
        beverage->print();
        std::cout << " + milk "<<milkCount <<" x "<<50;
    }
};

#endif //SEMINAR05_BEVERAGEWITHMILK_H
