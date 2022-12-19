//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//

#ifndef LABDIANABUN_DOMAIN_H
#define LABDIANABUN_DOMAIN_H

typedef struct{
    char* name;
    char* prod;
    int q;
}Mat;

/**
 * Functia creeaza un element de tip mat
 * @param: name - numele materiei
 *         prod - producatorul
 *         q    - cantitatea de adaugat
 * @return materia cu caracteristicile de mai sus
 * */
Mat* create_mat(char* name, char* prod, int q);

/**
 * Functia valideaza o posibila materie
 * @param name numele materiei
 * @param prod numele producatorului
 * @param q cantiatea
 * @return 0 sau 1, in functie de validitatea intrarii
 */
int validate(char* name, char* prod, int q);

/**
 * Functia distruge un element de tip mat
 * @param m materia de distrus
 */
void destroy_mat(Mat* m);


/**
 * Verifica egalitatea a doua materii
 * @param a prima materie
 * @param b a doua materie
 * @return 1 sau 0 in functie de valoarea lor de egalitate
 */
int equal_mat(Mat* a,Mat* b);

/**
 * Combina cantitatea a doua materii crescand valoarea unei
 * @param a prima materie
 * @param b a doua materie
 */
void inc_mat(Mat* a,Mat* b);

/**
 * Functia de filtrare specifica materiei
 * @param mat materia
 * @param crt criteriul
 * @param val valoarea
 * @return 0 sau 1
 */
int filter_fct(Mat* mat,char* crt, char* val);

/**
 * Copiaza o materie
 * @param a materia de copiat
 * @return o materie identica cu cea oferita ca parametru, fara vreo legatura cu aceasta
 */
Mat* copy_mat(Mat* a);

/**
 * Comparator dupa nume
 * @param a materia de comparat
 * @param b materia de comparat
 * @return 0 sau 1 in functie de cum se compara
 */
int compName(Mat* a,Mat* b);

/**
 * Comparator dupa Producator
 * @param a materia de comparat
 * @param b materia de comparat
 * @return 0 sau 1 in functie de cum se compara
 */
int compProd(Mat* a,Mat* b);

/**
 * Comparator dupa cantitate
 * @param a materia de comparat
 * @param b materia de comparat
 * @return 0 sau 1 in functie de cum se compara
 */
int compQ(Mat* a,Mat* b);

int compComb(Mat* a,Mat* b);

#endif //LABDIANABUN_DOMAIN_H
