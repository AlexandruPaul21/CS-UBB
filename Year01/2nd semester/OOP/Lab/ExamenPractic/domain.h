//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#ifndef EXAMENPRACTIC_DOMAIN_H
#define EXAMENPRACTIC_DOMAIN_H

#include <string>
#include <vector>

using namespace std;
/**
 * Entitatea problemei(Task-ul)
 */
class Task{
private:
    int id;
    string desc;
    string state;
    vector<string> pro;
public:
    /**
     * Constructorul Task ului
     * @param id id-ul taskului
     * @param desc descrierea taskului
     * @param state starea sa
     * @param pro programatorii ce lucreaza la el
     */
    Task(int id,string desc,string state,vector<string> pro):id{id},desc{desc},state{state},pro{pro}{}

    /**
     * Valideaza un set de date ce ar putea constitui un task
     * @param id id-ul candidat
     * @param desc descrierea candidata
     * @param state starea taskului
     * @param pro programatorii cel lucreaza la el
     * @raises ValidationException daca nu este valid
     */
    static void validate(int id,string desc,string state,vector<string> pro);

    /**
     * Gettere si settere
     */
    int getId() const;

    string getDesc() const;

    string getState() const;

    vector<string> getPro() const;

    void setState(string nw);
};

/**
 * Clasa pentru tratarea exceptiilor de validare
 */
class ValidationException{
private:
    string msg;
public:
    /**
     * Constrctor
     * @param msg mesajul de eroare
     */
    ValidationException(string msg):msg{msg}{}

    /**
     * Getter
     * @return mesaj
     */
    string getMesg(){
        return msg;
    }
};

/**
 * Functie de test
 */
void test_domain();

#endif //EXAMENPRACTIC_DOMAIN_H