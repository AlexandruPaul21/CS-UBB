//
// Created by Alexandru-Paul Sirbu on 26.03.2022.
//

#ifndef LAB06_7_UI_H
#define LAB06_7_UI_H

#include "service.h"
#include "recipe_class.h"

int get_pos_nr();

/**
 * Clasa de interfata cu utilizatorul
 */
class Console{
public:
    /**
     * Constructorul
     * @param srv un service
     */
    explicit Console(Service& srv) :srv{srv}{};

    /**
     * Afiseaza UI-ul
     */
    void show_ui() const;

private:
    /**
     * UI pentru adaugarea unei noi entitati
     */
    void add_ui() const;

    /**
     * UI pentru stergere
     */
    void del_ui() const;

    /**
     * UI pentru afisarea tutror entitatilor
     */
    void show_all() const;

    /**
     * UI pentru modificare
     */
    void mod_ui() const;

    /**
     * UI pentru cautare
     */
    void cautare() const;

    /**
     * UI pentru filtrare
     */
    void filter() const;

    /**
     * UI pentru sortare
     */
    void sort() const;

    /**
     * UI pentru recipe
     */
    void recipe() const;

    /**
     * UI pentru batch mode
     */
    void batch_mode() const;

    void undo_ui() const;

    Service &srv;
};

#endif //LAB06_7_UI_H
