//
// Created by Alexandru-Paul Sirbu on 12.04.2022.
//

#ifndef LAB06_7_RECIPE_CLASS_H
#define LAB06_7_RECIPE_CLASS_H

#include <vector>
#include <string>
#include "domain.h"

using std::vector;

/**
 * Clasa care implementeaza ideea de retea
 */
class Recipe{
private:
    vector<Medicine> comp;
public:
    /**
     * Default constructor
     */
    Recipe();

    /**
     * Returneaza toate medicamentele din reteta
     * @return vector de medicine cu medicamentele din reteta
     */
    [[nodiscard]]vector<Medicine> get_all()const;

    /**
     * Adauga un nou medicament in reteta
     * @param m medicamentul de adaugat
     */
    void add_to_recipe(const Medicine& m);

    /**
     * Goleste reteta
     */
    void empty_recipe();

    /**
     * Adauga random entitati la reteta
     * @param elems medicamentele disponibile
     * @param q numarul de medicamente ce trebuie adaugate
     */
    void random_add(const vector<Medicine>& elems,const int& q);

    /**
     * Salveaza in fisier entitatile
     * @param filename numele fisierului
     */
    void save_to_file(const string& filename);
};

#endif //LAB06_7_RECIPE_CLASS_H
