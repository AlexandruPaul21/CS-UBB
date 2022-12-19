//
// Created by Alexandru-Paul Sirbu on 05.04.2022.
//

#ifndef LAB06_7_VECTOR_MAN_H
#define LAB06_7_VECTOR_MAN_H

#include <exception>


template <class ElemType>
class Vector_Man{
private:
    ElemType* elem;
    int cp;
    int sze;
public:
    /**
     * Constructors and destructors
     */
    Vector_Man();
    explicit Vector_Man(int sz);

    //Vector_Man(const Vector_Man<ElemType>& ot);

    ~Vector_Man();

    /**
     * Functia asigura ca vectorul dinamic va avea tot timpul capacitatea de a stoca elemente
     */
    void ensure_cap();

    /**
     * Functia adauga un element la finalul listei
     * @param elm elemntul de adaugat
     */
    void push_back(const ElemType& elm);

    /**
     * Functia returneaza un pointer la inceputul zonei de memorie
     */
    ElemType* begin() const;

    /**
     * Functia returneaza un pointer la sfarsitul zonei de memorie
     */
    ElemType* end()const ;

    /**
     * Functia sterge un element de o pozitie data
     * @param pos pozitia
     */
    void erase(const int& pos);

    /**
     * Verifica daca containerul e gol
     * @return 1 sau 0 in functie de starea lui
     */
    [[nodiscard]]bool empty()const;

    /**
     * Returneaza nr de elemente din container
     * @return un intreg
     */
    [[nodiscard]]int size() const;

    /**
     * Suprascriem operatorul []
     */
    ElemType& operator[](int poz);

    //face o copie elementului actual
    void copy(Vector_Man<ElemType>& ot);

    //suprascriem egalul
    Vector_Man& operator=(const Vector_Man& ot);
};

#endif //LAB06_7_VECTOR_MAN_H
