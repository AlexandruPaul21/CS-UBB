//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#ifndef EXAMENPRACTIC_REPO_H
#define EXAMENPRACTIC_REPO_H

#include "domain.h"
#include <fstream>
#include <sstream>

/**
 * Clasa Repo
 */
class FileRepo{
private:
    string filename;
    vector<Task> tasks;
    /**
     * Salveaza in fisier
     */
    void save();
    /**
     * Incarca din fisier
     */
    void load();
public:
    /**
     * Constructor
     * @param file fisierul din care se citeste
     */
    FileRepo(string file):filename{file}{
        load();
    }

    /**
     * Adauga un task
     * @param s task ul
     */
    void add(Task s);

    /**
     * Returneaza o lista de taskuri
     */
    vector<Task>& get_all();

    /**
     * Verifica daca un id este valid
     * @param id id-ul de verificat
     * @raises DuplicateIdException daca id-ul este deja folosit
     */
    void validId(int id);

    /**
     * Schimba starea unui obiect
     * @param id id-ul sau
     * @param state noua stare
     */
    void changeState(int id,string state);
};

/**
 * Clasa exceptie
 */
class DuplicatedIdException{
private:
    string msg;
public:
    /**
     * Initializam exceptia cu un mesaj
     * @param msg
     */
    DuplicatedIdException(string msg):msg{msg}{}

    /**
     * Getter
     * @return mesajul
     */
    string getMsg(){
        return msg;
    }
};

/**
 * Transforma un numar intr-un string
 * @param s stringul
 * @return numarul
 */
int to_nr(string s);

/**
 * Functie de test
 */
void test_repo();

#endif //EXAMENPRACTIC_REPO_H