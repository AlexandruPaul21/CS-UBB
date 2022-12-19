//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//

#include "domain.h"
#include "service.h"
#include "repo.h"
#include <string.h>

bk_serv create_bk_serv(){
    bk_serv mat_store;
    mat_store.actual= create_list((destroy_fct) destroy_mat);
    mat_store.undo= create_list((destroy_fct)destroy_list);
    return mat_store;
}

int add_new_elem(bk_serv* ms, char* name, char* prod, int q){
    if(validate(name,prod,q)){
        MyList* toUndo= copy_list(ms->actual, (copy_fct) copy_mat);
        add_elem(ms->actual, (ElemType *) create_mat(name, prod, q), (eq_fct) equal_mat, (inc_fct) inc_mat);
        add_elem(ms->undo, (ElemType *) toUndo, (eq_fct) eq_lst, (inc_fct) inc_lst);
        return 1;
    }
    return 0;
}

int mod_ex_elem(bk_serv* ms,int poz, char* name, char* prod, int q){
    if(validate(name,prod,q) && 0<=poz && poz<ms->actual->size){
        Mat* mat= create_mat(name,prod,q);
        MyList* toUndo= copy_list(ms->actual, (copy_fct) copy_mat);
        mod_elem(ms->actual,poz,mat);
        add_elem(ms->undo, (ElemType *) toUndo, (eq_fct) eq_lst, (inc_fct) inc_lst);
        return 1;
    }
    return 0;
}

int delete_ex_elem(bk_serv* ms,int poz){
    if(0<=poz && poz<ms->actual->size){
        MyList* toUndo= copy_list(ms->actual, (copy_fct) copy_mat);
        del_elem(ms->actual,poz);
        add_elem(ms->undo, (ElemType *) toUndo, (eq_fct) eq_lst, (inc_fct) inc_lst);
        return 1;
    }
    return 0;
}

MyList* filter(MyList* lst,char* fil_crt,char* fil_val,fil_fct fil){
    if(strlen(fil_crt)<=0 || strlen(fil_val)<=0) return 0;
    MyList* fl_ls= create_list(lst->dest);
    for(int i=0; i<lst->size; ++i){
        if(fil(lst->elems[i],fil_crt,fil_val)){
            add_elem(fl_ls, (ElemType *) copy_mat(lst->elems[i]), (eq_fct) equal_mat, (inc_fct) inc_mat);
        }
    }
    return fl_ls;
}

MyList* sort(MyList* lst, sort_fct cmp,int desc){
    MyList* sorted= copy_list(lst, (copy_fct) copy_mat);
    for(int i=0; i<sorted->size; ++i){
        for(int j=i+1; j<sorted->size; ++j){
            if(desc-cmp(sorted->elems[i],sorted->elems[j])){
                ElemType* aux=sorted->elems[i];
                sorted->elems[i]=sorted->elems[j];
                sorted->elems[j]=aux;
            }
        }
    }
    return sorted;
}

int undo(bk_serv* ms){
    if(ms->undo->size<=0){
        return 0;
    }
    MyList* new_lst= remove_last(ms->undo);
    destroy_list(ms->actual);
    ms->actual=new_lst;
    return 1;
}

void destroy_srv(bk_serv* ms){
    destroy_list(ms->actual);
    destroy_list(ms->undo);
}

