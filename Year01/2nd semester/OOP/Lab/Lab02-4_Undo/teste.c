//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//

#include "domain.h"
#include "repo.h"
#include "service.h"
#include "teste.h"
#include "assert.h"
#include <stdio.h>
#include <string.h>

void test_all(){
    test_domain();
    test_repo();
    test_service();
    printf("Le-o luat\n");
}

void test_domain(){
    //test_create_mat
    Mat* mat= create_mat("Vanilie","Dormeo",2);
    assert(strcmp(mat->name,"Vanilie")==0);
    assert(strcmp(mat->prod,"Dormeo")==0);
    assert(mat->q==2);
    destroy_mat(mat);

    //test_validate
    assert(validate("","Dormeo",3)==0);
    assert(validate("Name","",4)==0);
    assert(validate("Naame","Prod",-3)==0);
    assert(validate("Nume","Firma",4));

    //test_eq
    Mat* mat1,*mat4,*mat2,*mat3;
    mat1= create_mat("Vanilie","Otker",5);
    mat2= create_mat("Vanilie","Otker",7);
    mat3= create_mat("Vanilie","Lala",7);
    mat4= create_mat("Prafuri","Lala",8);
    assert(equal_mat(mat1,mat2));
    assert(equal_mat(mat2,mat3)==0);
    assert(equal_mat(mat3,mat4)==0);
    assert(equal_mat(mat1,mat3)==0);

    inc_mat(mat1,mat2);
    assert(mat1->q==12);

    destroy_mat(mat1);
    destroy_mat(mat2);
    destroy_mat(mat3);
    destroy_mat(mat4);

    //test_copy
    Mat* mat5= create_mat("Vanilie","Otker",5);
    Mat* mat6= copy_mat(mat5);
    destroy_mat(mat5);
    mat5=create_mat("Cacao","Otker",5);
    assert(strcmp(mat5->name,"Cacao")==0);

    assert(strcmp(mat6->name,"Vanilie")==0);

    //test filter
    Mat* mat_f=create_mat("Alex","Nivea",25);

    assert(filter_fct(mat_f,"name","A")==0);
    assert(filter_fct(mat_f,"nume","Ale")==0);

    assert(filter_fct(mat_f,"nume","A")==1);
    assert(filter_fct(mat_f,"nume","B")==0);

    assert(filter_fct(mat_f,"producator","Nivea")==1);
    assert(filter_fct(mat_f,"producator","Niveea")==0);

    assert(filter_fct(mat_f,"cantitate","25")==1);
    assert(filter_fct(mat_f,"cantitate","25c")==0);
    assert(filter_fct(mat_f,"cantitate","10")==0);

    destroy_mat(mat5);
    destroy_mat(mat6);
    destroy_mat(mat_f);
}

void test_repo(){
    //test_create_list
    MyList* lst= create_list((destroy_fct) destroy_mat);
    destroy_list(lst);

    //test_add_elem
    MyList* lst1= create_list((destroy_fct) destroy_mat);
    add_elem(lst1, (ElemType *) create_mat("Vanilie", "Otker", 5), (eq_fct) equal_mat, (inc_fct) inc_mat);
    assert(lst1->size==1);
    add_elem(lst1, (ElemType *) create_mat("Vanilie", "Otker", 7), (eq_fct) equal_mat, (inc_fct) inc_mat);
    assert(lst1->size==1);

    assert(strcmp(((Mat*)(lst1->elems[0]))->name,"Vanilie")==0);
    assert(strcmp(((Mat*)(lst1->elems[0]))->prod,"Otker")==0);
    assert(((Mat*)(lst1->elems[0]))->q==12);

    add_elem(lst1, (ElemType *) create_mat("Praf de copt", "Mic", 5), (eq_fct) equal_mat, (inc_fct) inc_mat);
    assert(lst1->size==2);

    assert(strcmp(((Mat*)(lst1->elems[1]))->name,"Praf de copt")==0);
    assert(strcmp(((Mat*)(lst1->elems[1]))->prod,"Mic")==0);
    assert(((Mat*)(lst1->elems[1]))->q==5);

    Mat* mat1=remove_last(lst1);
    assert(lst1->size==1);

    assert(strcmp(mat1->name,"Praf de copt")==0);
    assert(strcmp(mat1->prod,"Mic")==0);
    assert(mat1->q == 5);

    mod_elem(lst1,0,mat1);
    assert(lst1->size==1);

    assert(strcmp(((Mat*)(lst1->elems[0]))->name,"Praf de copt")==0);
    assert(strcmp(((Mat*)(lst1->elems[0]))->prod,"Mic")==0);
    assert(((Mat*)(lst1->elems[0]))->q==5);

    add_elem(lst1, (ElemType *) create_mat("Praf de fiert", "Mediu", 8), (eq_fct) equal_mat, (inc_fct) inc_mat);
    add_elem(lst1, (ElemType *) create_mat("Praf de pff", "Mare", 7), (eq_fct) equal_mat, (inc_fct) inc_mat);

    del_elem(lst1,2);
    inc_lst(lst1);
    assert(lst1->size==2);

    add_elem(lst1, (ElemType *) create_mat("Praf de alala", "Mae", 8), (eq_fct) equal_mat, (inc_fct) inc_mat);
    add_elem(lst1, (ElemType *) create_mat("Praf de balal", "Marere", 7), (eq_fct) equal_mat, (inc_fct) inc_mat);

    del_elem(lst1,1);
    assert(lst1->size==3);

    MyList* new_list= copy_list(lst1, (copy_fct) copy_mat);

    mod_elem(new_list,1, create_mat("Alex","Toni",4));
    mod_elem(lst1,1, create_mat("Toni","Alex",4));
    assert(strcmp(((Mat*)(new_list->elems[1]))->name,"Alex")==0);
    assert(strcmp(((Mat*)(lst1->elems[1]))->name,"Toni")==0);

    destroy_list(lst1);
    destroy_list(new_list);
}

void test_service(){
    //test create srv
    bk_serv srv=create_bk_serv();
    destroy_srv(&srv);

    //test add srv
    bk_serv ms=create_bk_serv();
    assert(add_new_elem(&ms,"","Nivea",5)==0);
    assert(add_new_elem(&ms,"Crema","Nivea",5)==1);
    assert(add_new_elem(&ms,"Lotiune","Fa",7)==1);

    assert(strcmp(((Mat*)(ms.actual->elems[0]))->name,"Crema")==0);
    assert(strcmp(((Mat*)(ms.actual->elems[0]))->prod,"Nivea")==0);
    assert(((Mat*)(ms.actual->elems[0]))->q==5);

    assert(ms.undo->size==2);
    assert(strcmp(((Mat*)(((MyList*)ms.undo->elems[1])->elems[0]))->name,"Crema")==0);
    assert(strcmp(((Mat*)(((MyList*)ms.undo->elems[1])->elems[0]))->prod,"Nivea")==0);
    assert(((Mat*)(((MyList*)ms.undo->elems[1])->elems[0]))->q==5);


    //test mod
    assert(mod_ex_elem(&ms,0,"Cremuta","Niveuta",8)==1);
    assert(strcmp(((Mat*)(ms.actual->elems[0]))->name,"Cremuta")==0);
    assert(strcmp(((Mat*)(ms.actual->elems[0]))->prod,"Niveuta")==0);
    assert(((Mat*)(ms.actual->elems[0]))->q==8);

    assert(add_new_elem(&ms,"Alex","Lexutz",9)==1);
    assert(strcmp(((Mat*)(((MyList*)ms.undo->elems[3])->elems[0]))->name,"Cremuta")==0);
    assert(strcmp(((Mat*)(((MyList*)ms.undo->elems[3])->elems[0]))->prod,"Niveuta")==0);
    assert(((Mat*)(((MyList*)ms.undo->elems[3])->elems[0]))->q==8);

    assert(mod_ex_elem(&ms,-2,"Alex","Lexutz",9)==0);

    //test delete
    assert(delete_ex_elem(&ms,-5)==0);
    assert(delete_ex_elem(&ms,0)==1);
    assert(add_new_elem(&ms,"Aleex","Lexxutz",9)==1);
    assert(ms.actual->size==3);

    assert(strcmp(((Mat*)(((MyList*)ms.undo->elems[5])->elems[0]))->name,"Lotiune")==0);
    assert(strcmp(((Mat*)(((MyList*)ms.undo->elems[5])->elems[0]))->prod,"Fa")==0);
    assert(((Mat*)(((MyList*)ms.undo->elems[5])->elems[0]))->q==7);

    assert(undo(&ms)==1);
    assert(ms.actual->size==2);
    assert(ms.undo->size==5);
    assert(undo(&ms)==1);
    assert(undo(&ms)==1);
    assert(undo(&ms)==1);
    assert(undo(&ms)==1);
    assert(undo(&ms)==1);
    assert(undo(&ms)==0);

    //filter
    assert(add_new_elem(&ms,"Crema","Nivea",5)==1);
    assert(add_new_elem(&ms,"Lotiune","Nivea",7)==1);
    assert(add_new_elem(&ms,"Curucuma","Fa",9)==1);

    MyList* ans=filter(ms.actual, "nume", "C", (fil_fct) filter_fct);
    assert(ans->size==2);
    assert(strcmp(((Mat*)(ans->elems[0]))->name,"Crema")==0);
    assert(strcmp(((Mat*)(ans->elems[1]))->name,"Curucuma")==0);
    destroy_list(ans);

    MyList *ans1=filter(ms.actual, "producator", "Nivea", (fil_fct) filter_fct);
    assert(ans1->size==2);
    assert(strcmp(((Mat*)(ans1->elems[0]))->name,"Crema")==0);
    assert(strcmp(((Mat*)(ans1->elems[1]))->name,"Lotiune")==0);
    destroy_list(ans1);

    MyList *ans3=filter(ms.actual, "cantitate", "10", (fil_fct) filter_fct);
    assert(ans3->size==3);
    destroy_list(ans3);

    //test sorted
    MyList* sorted= sort(ms.actual, (sort_fct) compName,0);
    assert(sorted->size==3);
    assert(strcmp(((Mat*)(sorted->elems[0]))->name,"Crema")==0);
    assert(strcmp(((Mat*)(sorted->elems[1]))->name,"Curucuma")==0);
    assert(strcmp(((Mat*)(sorted->elems[2]))->name,"Lotiune")==0);
    destroy_list(sorted);

    sorted=sort(ms.actual, (sort_fct) compProd,0);
    assert(sorted->size==3);
    assert(strcmp(((Mat*)(sorted->elems[0]))->prod,"Fa")==0);
    assert(strcmp(((Mat*)(sorted->elems[1]))->prod,"Nivea")==0);
    assert(strcmp(((Mat*)(sorted->elems[2]))->prod,"Nivea")==0);
    destroy_list(sorted);

    sorted=sort(ms.actual, (sort_fct) compQ,0);
    assert(sorted->size==3);
    assert(((Mat*)(sorted->elems[0]))->q==5);
    assert(((Mat*)(sorted->elems[1]))->q==7);
    assert(((Mat*)(sorted->elems[2]))->q==9);
    destroy_list(sorted);

    sorted= sort(ms.actual, (sort_fct) compName,1);
    assert(sorted->size==3);
    assert(strcmp(((Mat*)(sorted->elems[2]))->name,"Crema")==0);
    assert(strcmp(((Mat*)(sorted->elems[1]))->name,"Curucuma")==0);
    assert(strcmp(((Mat*)(sorted->elems[0]))->name,"Lotiune")==0);
    destroy_list(sorted);

    sorted=sort(ms.actual, (sort_fct) compProd,1);
    assert(sorted->size==3);
    assert(strcmp(((Mat*)(sorted->elems[2]))->prod,"Fa")==0);
    assert(strcmp(((Mat*)(sorted->elems[1]))->prod,"Nivea")==0);
    assert(strcmp(((Mat*)(sorted->elems[0]))->prod,"Nivea")==0);
    destroy_list(sorted);

    sorted=sort(ms.actual, (sort_fct) compQ,1);
    assert(sorted->size==3);
    assert(((Mat*)(sorted->elems[2]))->q==5);
    assert(((Mat*)(sorted->elems[1]))->q==7);
    assert(((Mat*)(sorted->elems[0]))->q==9);
    destroy_list(sorted);

    destroy_srv(&ms);
}