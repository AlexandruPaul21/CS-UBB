//
// Created by Alexandru-Paul Sirbu on 01.05.2022.
//

#ifndef LAB10_11V2_DOMAIN_H
#define LAB10_11V2_DOMAIN_H

#include<vector>
#include<sstream>

using namespace std;

/**
 * Clasa de exceptii
 */
class ValidationException{
public:
    explicit ValidationException(const vector<string>& errors) : msg{errors}{}

    friend ostream& operator<<(ostream&out,const ValidationException& ex);

    vector <string> msg;
};

ostream& operator<<(ostream&out,const ValidationException& ex);

class BadLuckException{
private:
    string msg;
public:
    explicit BadLuckException(const string& str): msg{str}{}

    friend ostream& operator<<(ostream& out,const BadLuckException& ex);
};

ostream& operator<<(ostream& out,const BadLuckException& ex);

/**
 * Clasa de entitati
 */
class Medicine{
public:

    /**
     * Constructor
     * @param name numele med.
     * @param prod prod. med
     * @param subst subst. act. a med
     * @param price pretul med.
     */
    Medicine();
    Medicine(const string& name, const string& prod, const string& subst, const int& price);

    Medicine(const Medicine& m)=default;
    //gettee
    [[nodiscard]]string get_name() const{
        return name;
    }
    [[nodiscard]] string get_prod() const{
        return prod;
    }
    [[nodiscard]]string get_subst() const{
        return subst;
    }
    [[nodiscard]]int get_price() const{
        return price;
    }

    //suprascriem operatorul de egalitate
    bool operator==(const Medicine& ot);

    //egalitate
    Medicine& operator=(const Medicine& ot);

private:
    string name;
    string prod;
    string subst;
    int price;
};

/**
 * Clasa Validator
 */
class Validator{
public:
    /**
     * Functia de validare
     * @param name numele de validat
     * @param prod produsul
     * @param subst subst. activa
     * @param price pretul
     * @return un string de erori
     */
    static void validate(const string& name, const string& prod, const string& subst, const int& price) ;
};

/**
 * Verfica daca un string are litere
 * @param S stringul de verificat
 * @return 0 sau 1 ca raspuns
 */
bool has_letters(const string& S);

string toMyString(vector<string> msg);

void test_domain();

#endif //LAB10_11V2_DOMAIN_H
