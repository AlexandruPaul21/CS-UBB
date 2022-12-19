//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#ifndef EXAMENPRACTIC_SERVICE_H
#define EXAMENPRACTIC_SERVICE_H

#include "repo.h"
#include "observer.h"

/**
 * Clasa Service
 */
class Service:public Observable{
private:
    FileRepo& fr;
public:
    /**
     * Constructor
     * @param fr file repo
     */
    Service(FileRepo& fr):fr{fr}{}

    /**
     * Adauga o noua entitate
     * @param id id-ul entitatii
     * @param desc descrierea entiatii
     * @param state starea entiatii
     * @param pro programatorii
     */
    void add(int id,string desc,string state,vector<string> pro);

    /**
     * Returneaza toate elementele existente
     * @return
     */
    vector<Task>& get_all();

    /**
     * Verifica daca un id este valid
     * @param id id-ul de verificat
     */
    void validId(int id);

    /**
     * Filtreaza numele programatorilor dupa un text data
     * @param pat pattern-ul de verificat
     * @return o lista de task uri
     */
    vector<Task> filter_by_pro(string pat);

    /**
     * Schimba starea unui obiect
     * @param id id-ul obiectului
     * @param new_state noua stare
     */
    void changeState(int id,string new_state);
};

/**
 * Functie de test
 */
void test_service();


#endif //EXAMENPRACTIC_SERVICE_H