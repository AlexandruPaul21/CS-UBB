//
// Created by Alexandru-Paul Sirbu on 26.03.2022.
//

#ifndef LAB06_7_REPO_H
#define LAB06_7_REPO_H

#include <iostream>
#include <vector>
#include "domain.h"
#include <sstream>
#include <map>
//#include "vector_man.h"
//#include "vector_man.cpp"

using namespace std;

class AbsRepo{
public:
    virtual void add_medicine(const Medicine& a)=0;

    virtual void modify_medicine(const Medicine& a,int poz)=0;

    virtual void delete_medicine(int poz)=0;

    virtual vector<Medicine>& get_elems()=0;

    virtual ~AbsRepo()=default;
};


/**
 * Clasa repository
 */
class Repo:public AbsRepo{
public:
    Repo()=default;
    //Repo(const Repo& ot){*this=ot;}
    /**
     * Adauga un medicament in repo
     * @param a medicamentul de adaugat
     */
    virtual void add_medicine(const Medicine& a) override;

    /**
     * Modifica un medicament deja existent
     * @param a medicamentul de modificat
     * @param poz pozitia medicamentului
     */
    virtual void modify_medicine(const Medicine& a, int poz) override;

    /**
     * Functie getter
     * @return un vector cu elemente de tip medcicine
     */
    vector<Medicine>& get_elems() override;

    /**
     * Sterge medicamentul de pe o pozitie
     * @param poz pozitia medicamentului
     */
    virtual void delete_medicine(int poz) override;

    virtual ~Repo()=default;

private:
    vector<Medicine> elems;
};


class FileRepo: public Repo{
private:
    string filename;

    void load_from_file();

    void save_to_file();

public:
    FileRepo()=default;
    explicit FileRepo(string fn);
    //FileRepo(const FileRepo& ot) : Repo(ot) {*this=ot;};

    ~FileRepo() override =default;

    void add_medicine(const Medicine& a) override{
        Repo::add_medicine(a);
        save_to_file();
    }

    void modify_medicine(const Medicine& a,int poz) override{
        Repo::modify_medicine(a,poz);
        save_to_file();
    }

    void delete_medicine(int poz) override{
        Repo::delete_medicine(poz);
        save_to_file();
    }

};

class RepoProb:public AbsRepo{
private:
    float prob;
    map<int,Medicine> elems;
    void det_luck();
public:
    RepoProb()=default;

    explicit RepoProb(float chance);

    void add_medicine(const Medicine& m) override;

    void modify_medicine(const Medicine& m, int poz) override;

    void delete_medicine(int poz) override;

    vector<Medicine>& get_elems() override;
};

/**
 * Clasa custom de exceptie
 */
class RepoException{
    vector<string> msg;
public:
    explicit RepoException(const vector<string>& errors): msg{errors}{}

    friend ostream& operator<<(ostream& out,const RepoException& ex);
};

ostream& operator<<(ostream& out, const RepoException& ex);




#endif //LAB06_7_REPO_H
