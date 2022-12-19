//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//

#ifndef LABDIANABUN_REPO_H
#define LABDIANABUN_REPO_H

/**
 * Definim tipuri de date pentru programarea generica
 */

typedef void* ElemType;

typedef void(*destroy_fct)(ElemType);

typedef ElemType(*copy_fct)(ElemType);

typedef int(*eq_fct)(ElemType,ElemType);

typedef void(*inc_fct)(ElemType,ElemType);

typedef void(*print_fct)(ElemType);

typedef struct{
    ElemType* elems;
    int size;
    int cp;
    destroy_fct dest;
}MyList;

/**
 * Creeaza o noua lista de tip MyList
 * @param f functia de distrugere a elementelor
 * @return un element de tip MyList
 */
MyList* create_list(destroy_fct f);

/**
 * Extinde spatiul de memorare
 * @param lst lista in care se extinde
 */
void ensure_cap(MyList* lst);

/**
 * Adauga un nou elment in list
 * @param lst lista in care se adauga
 * @param elem elementul ce se adauga
 * @param eq functia de egalitate
 * @param inc functia de incrementare
 */
void add_elem(MyList* lst,ElemType* elem,eq_fct eq,inc_fct inc);

/**
 * Modifica un element
 * @param lst lista in care are loc modificare
 * @param poz pozitia la care se realizeaza modificarea
 * @param elem elemntul ce trebuie inlocuit
 */
void mod_elem(MyList* lst,int poz, ElemType elem);

/**
 * Sterge un element dintr-o list
 * @param lst lista
 * @param poz pozitia pe care se sterge
 */
void del_elem(MyList* lst,int poz);

/**
 * Extrage ultimul element din lista
 * @param lst lista din care se extrage
 * @return Elementul extras
 */
ElemType remove_last(MyList* lst);

/**
 * Distruge lista
 * @param lst lista de distrus
 */
void destroy_list(MyList* lst);

/**
 * Realizeaza o copie total independenta a listei
 * @param lst lista de modificat
 * @param cpy functia de copiere
 * @return O lista identica dar independenta de cea initiala
 */
MyList* copy_list(MyList* lst,copy_fct cpy);

/**
 * Functia este facuta ca sa functioneze adaugara la elemente de tip lista
 */
int eq_lst(MyList* lst1,MyList* lst2);

/**
 * Functia este facuta ca sa functioneze adaugara la elemente de tip lista
 */
void inc_lst(MyList* lst);

#endif //LABDIANABUN_REPO_H
