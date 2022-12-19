//
// Created by Alexandru-Paul Sirbu on 12.04.2022.
//

#include "recipe_class.h"
#include <random>
#include <fstream>

Recipe::Recipe() {
    comp.clear();
}

void Recipe::add_to_recipe(const Medicine &m) {
    comp.push_back(m);
}

void Recipe::empty_recipe() {
    comp.clear();
}

void Recipe::random_add(const vector<Medicine>& elems,const int& q) {
    std::mt19937 mt{ std::random_device{}() };
    std::uniform_int_distribution<> dist(0, elems.size()-1);
    for(int i=0; i<q; ++i){
        int nr=dist(mt);
        add_to_recipe(elems[nr]);
    }
}

void Recipe::save_to_file(const string& filename) {
    ofstream fout(filename);
    fout<<"First cell\n";
    fout<<"Nrcrt. Nume Prod Subst Pret\n";
    int i=0;
    for(auto& med : comp){
        fout<<i++<<" "<<med.get_name()<<" "<<med.get_prod()<<" "<<med.get_subst()<<" "<<med.get_price()<<"\n";
    }
}

vector<Medicine> Recipe::get_all() const {
    return comp;
}
