//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//

#ifndef LABDIANABUN_SERVICE_H
#define LABDIANABUN_SERVICE_H

#include "repo.h"
#include "domain.h"

typedef int(*fil_fct)(ElemType,char*,char*);

typedef int(*sort_fct)(ElemType a,ElemType b);

typedef struct{
    MyList* actual;
    MyList* undo;
}bk_serv;

/**
 * Creeaza un service pentru o patiserie
 * @return service-ul
 */
bk_serv create_bk_serv();

/**
 * Adauga un nou element in service
 * @param ms service-ul in care se adauga
 * @param name numele
 * @param prod producatorul
 * @param q cantitatea
 * @return 0 sau 1 in functie de efectuarea sau nu a adaugarii
 */
int add_new_elem(bk_serv* ms, char* name, char* prod, int q);

/**
 * Modifica o materie deja existenta
 * @param ms serivce-ul
 * @param poz pozitia pe care se afla materia de modificat
 * @param name numele materiei
 * @param prod producatorul materiei
 * @param q cantitatea de materie
 * @return 0 sau 1 in functie de reusita operatiei
 */
int mod_ex_elem(bk_serv* ms,int poz, char* name, char* prod, int q);

/**
 * Sterge un element din service
 * @param ms service-ul de sters
 * @param poz pozitia de sters
 * @return 0 sau 1 in functie de reusita operatiei
 */
int delete_ex_elem(bk_serv* ms,int poz);

/**
 * Realizeaza un undo al ultimei operatii
 * @param ms service-ul de sters
 * @return 0 sau 1 in functie de reusita operatiei
 */
int undo(bk_serv* ms);

/**
 * FFiltreaza elementele dupa o functie si o valoarea
 * @param lst lista de tip MyList cu care se filtreaza
 * @param fil_crt criteriul de filtrare
 * @param fil_val valoarea de filtrare
 * @param fil functia de filtrare
 * @return sirul cu elmenete dupa filtrare
 */
MyList* filter(MyList* lst,char* fil_crt,char* fil_val,fil_fct fil);

/**
 * Sorteaza elementele dupa anumite criterii
 * @param lst lista de sortat
 * @param cmp functia de comparare
 * @param desc 1 - desc, 0 cresc
 * @return lista sortata
 */
MyList* sort(MyList* lst, sort_fct cmp,int desc);

/**
 * Destructor pentru service
 * @param ms service-ul de distrus
 */
void destroy_srv(bk_serv* ms);

#endif //LABDIANABUN_SERVICE_H
