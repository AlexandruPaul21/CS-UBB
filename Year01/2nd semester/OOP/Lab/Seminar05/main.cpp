#include <iostream>
#include <fstream>
#include "beverage.h"

using std::ifstream;

int main() {
    std::cout << Beverage("console");
    std::cout << std::endl;
//    std::ofstream f2("/Users/alex/Desktop/Seminar05/out.txt");
//    if (!f2.is_open()) {
//        std::cout << "sometihg wronga" << std::endl;
//        return 1;
//    }
//
//    f2<<Beverage("written");
//    f2.close();


//    ifstream f("/Users/alex/Desktop/Seminar05/main.cpp");
//    if (!f.is_open()) {
//        std::cout << "sometihg wronga" << std::endl;
//        return 1;
//    }
//    std::string s;
//    while (!f.eof()) {
//        std::getline(f, s);
//        //std::cout << s<<std::endl;
//    }
//    f.close();



    ifstream f3("/Users/alex/Desktop/Seminar05/out.txt");
    Beverage b;
    if (!f3.is_open()) {
        std::cout << "sometihg wronga" << std::endl;
        return 1;
    }
    f3 >> b;
    b.print();
    std::cout << std::endl;
    f3.close();
    Beverage b2;
    std::cin >> b2;
    b2.print();
    return 0;
}
