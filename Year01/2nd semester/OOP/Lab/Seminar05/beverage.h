//
// Created by Alexandru-Paul Sirbu on 19.04.2022.
//

#ifndef SEMINAR05_BEVERAGE_H
#define SEMINAR05_BEVERAGE_H

#include <string>
#include <iostream>
#include <sstream>

using std::string;

class Beverage {
    string description;
    int whatever;
public:
    Beverage(const string &desc) : description{desc} { this->whatever = 123210; };

    Beverage() = default;

    virtual double price() { return 150; }

    void print() {
        std::cout << description << " for $" << std::to_string(this->price()) << " " << whatever;
    }

    friend std::ostream &operator<<(std::ostream &s, const Beverage &b) {
        s << b.description << ";" << b.whatever;
        return s;
    }

    friend std::istream &operator>>(std::istream &is, Beverage &b) {
        std::string s;
        std::string s1;

        std::getline(is, s, ';');
        std::getline(is, s1, ';');

        b.description = s;
        b.whatever = std::stoi(s1);
        return is;
    }
};


#endif //SEMINAR05_BEVERAGE_H
