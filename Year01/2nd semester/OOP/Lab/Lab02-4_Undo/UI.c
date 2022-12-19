//
// Created by Alexandru-Paul Sirbu on 24.03.2022.
//

#include "domain.h"
#include "repo.h"
#include "UI.h"
#include "service.h"
#include <stdio.h>
#include <string.h>

/**
 * Afiseaza o lista de tip MyList
 * @param lst lista
 * @param print functia de afisare
 */
void print_list(MyList* lst,print_fct print){
    for(int i=0; i<lst->size; ++i){
        printf("INDEX: %d  ",i);
        print(lst->elems[i]);
    }
}

/**
 * Afiseaza un element de tip mat
 * @param m materia de afisat
 */
void print_mat(Mat* m){
    printf("Nume: %s  Producator: %s  Cantitate: %d\n",m->name,m->prod,m->q);
}

/**
 * Implementeaza UI pentru adaugare
 * @param srv service-ul in care se adauga
 */
void add(bk_serv* srv){
    char name[40];
    printf("Introduceti denumirea materiei: ");
    scanf("%s",name);

    char prod[40];
    printf("Introduceti producatorul: ");
    scanf("%s",prod);

    int q=0;
    printf("Introdeuceti cantitatea: ");
    scanf("%d",&q);

    if(add_new_elem(srv,name,prod,q)==1){
        printf("Adaugare efectuata cu succes\n");
        return;
    }
    printf("Eroare!\n");
}

/**
 * Implementeaza UI pentru modificare
 * @param srv service-ul curent
 */
void modify(bk_serv* srv){
    int poz=0;
    printf("Introduceti pozitia de modificat: ");
    scanf("%d",&poz);

    char name[40];
    printf("Introduceti denumirea materiei: ");
    scanf("%s",name);

    char prod[40];
    printf("Introduceti producatorul: ");
    scanf("%s",prod);

    int q=0;
    printf("Introdeuceti cantitatea: ");
    scanf("%d",&q);

    if(mod_ex_elem(srv,poz,name,prod,q)==1){
        printf("Modificare efectuata cu succes\n");
        return;
    }
    printf("Eroare!\n");
}

/**
 * Implementeaza UI pentru stergere
 * @param srv service-ul curent
 */
void delete(bk_serv* srv){
    int poz=0;
    printf("Introduceti pozitia de sters: ");
    scanf("%d",&poz);

    if(delete_ex_elem(srv,poz)==1){
        printf("Stergere efectuata cu succes\n");
        return;
    }
    printf("Eroare!\n");
}

/**
 * Implementeaza UI pentru filtrare
 * @param srv service-ul curent
 */
void filterr(bk_serv* srv){
    printf("Comenzi filtrare: nume, producator, cantitate\n");

    char cmd[20];
    printf("Comanda dvs: ");
    scanf("%s",cmd);

    char val[20];
    printf("Valoarea dupa care se filtreaza: ");
    scanf("%s",val);

    MyList* ans=filter(srv->actual, cmd, val, (fil_fct) filter_fct);
    if(ans->size==0){
        printf("Filtrarea nu a reusit, consultati help\n");
    } else {
        print_list(ans, (print_fct) print_mat);
    }
    destroy_list(ans);

}

/**
 * Implementeaza UI pentru sort
 * @param srv service-ul curent
 */
void sortt(bk_serv* srv){
    printf("Criterii sortare disponibile: nume, producator, cantitate, special777\n");
    printf("Optiunea dvs: ");

    char op[20];
    scanf("%s",op);
    printf("0 - crescator\n1 - descrescator\n");

    int mon=0;
    printf("Optiunea dvs: ");
    scanf("%d",&mon);

    MyList* ans;

    if(strcmp(op,"nume")==0){
        ans=sort(srv->actual, (sort_fct) compName, mon);
    } else if(strcmp(op,"producator")==0){
        ans=sort(srv->actual, (sort_fct) compProd, mon);
    } else if(strcmp(op,"cantitate")==0){
        ans=sort(srv->actual, (sort_fct) compQ, mon);
    } else if(strcmp(op,"special777")==0){
        ans=sort(srv->actual, (sort_fct) compQ, mon);
    }
    else {
        printf("Eroare! Consultati help!\n");
        return;
    }
    if(ans->size==0){
        printf("Eroare!");
    } else {
        printf("-----------------------Lista sortata----------------------------\n");
        print_list(ans, (print_fct) print_mat);
        printf("----------------------------------------------------------------\n");
    }
    destroy_list(ans);
}

/**
 * Implementeaza UI pentru do_undo
 * @param srv service-ul curent
 */
void do_undo(bk_serv* srv){
    if(undo(srv)==1){
        printf("Undo efectuat cu succes!\n");
    } else {
        printf("Eroare!\n");
    }
}

void show_ui(){
    printf("\n---- Bine ati venit! ----\n");
    int done=0, show=0;
    char cmd[10];
    bk_serv srv=create_bk_serv();
    while(!done) {
        if (show && srv.actual->size > 0) {
            printf("-----------------------Lista actuala----------------------------\n");
            print_list(srv.actual, (print_fct) print_mat);
            printf("----------------------------------------------------------------\n");
        }
        printf("Comenzi disponibile: add, modify, delete, filter, sort, undo, exit, show, help\n");
        printf("Introduceti comanda: ");
        scanf("%s", cmd);
        if (strcmp(cmd, "exit") == 0) {
            done = 1;
        } else if (strcmp(cmd, "add") == 0) {
            add(&srv);
        } else if (strcmp(cmd, "modify") == 0) {
            modify(&srv);
        } else if (strcmp(cmd, "delete") == 0) {
            delete(&srv);
        } else if (strcmp(cmd, "filter") == 0) {
            filterr(&srv);
        } else if (strcmp(cmd, "sort") == 0) {
            sortt(&srv);
        } else if (strcmp(cmd, "undo") == 0) {
            do_undo(&srv);
        } else if (strcmp(cmd, "help") == 0) {
            printf("-----------------------Comenzi disp:----------------------------\n");
            printf("[add] - adauga o noua materie in lista\n");
            printf("[modify] - modifica o materie existenta in lista(se da indexul ei)\n");
            printf("[delete] - se sterge o materie existenta (se da indexul ei)\n");
            printf("[filter] - se filtreaza valorile (nume - dupa prima litera, producator- complet, cantitate -mai mic decat input)\n");
            printf("[sort] - se sorteaza valorile dupa orice camp\n");
            printf("[undo] -  se reface ultima operatie\n");
            printf("[show] -  se afiseaza intrarile existente\n");
            printf("[exit] - iesirea din aplicatie\n");
            printf("----------------------------------------------------------------\n");
        } else if (strcmp(cmd, "show") ==0 ){
            show=1-show;
        }else {
            printf("Eroare! Consultati help pentru ajutor!\n");
        }
    }
    destroy_srv(&srv);
}
