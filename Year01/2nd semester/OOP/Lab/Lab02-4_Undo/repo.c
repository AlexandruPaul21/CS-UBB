//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//

#include "repo.h"
#include "domain.h"
#include <stdlib.h>

MyList* create_list(destroy_fct f){
    MyList* new_lst= malloc(sizeof(MyList));
    new_lst->cp=2;
    new_lst->size=0;
    new_lst->elems= malloc(new_lst->cp*sizeof(ElemType));
    new_lst->dest=f;
    return new_lst;
}

void ensure_cap(MyList* lst){
    if(lst->size==lst->cp){
        lst->cp*=2;
        ElemType* temp= malloc(lst->cp*sizeof(ElemType));
        for(int i=0; i<lst->size; ++i){
            temp[i]=lst->elems[i];
        }
        free(lst->elems);
        lst->elems=temp;
    }
}

void add_elem(MyList* lst,ElemType* elem,eq_fct eq,inc_fct inc){
    int ok=0;
    for(int i=0; i<lst->size; ++i){
        if(eq(lst->elems[i],elem)){
            inc(lst->elems[i],elem);
            ok=1;
        }
    }
    if (ok) {
        lst->dest(elem);
        return;
    }
    ensure_cap(lst);
    lst->elems[lst->size++]=elem;
}

ElemType remove_last(MyList* lst){
    ElemType e=lst->elems[lst->size-1];
    --lst->size;
    return e;
}

void mod_elem(MyList* lst,int poz, ElemType elem){
    lst->dest(lst->elems[poz]);
    lst->elems[poz]=elem;
}

void del_elem(MyList* lst,int poz){
    lst->dest(lst->elems[poz]);
    for(int i=poz+1; i<lst->size; ++i){
        lst->elems[i-1]=lst->elems[i];
    }
    --lst->size;
}

void destroy_list(MyList* lst){
    for(int i=0; i<lst->size; ++i){
        lst->dest(lst->elems[i]);
    }
    free(lst->elems);
    free(lst);
}

MyList* copy_list(MyList* lst,copy_fct cpy){
    MyList* ret= create_list(lst->dest);
    for(int i=0; i<lst->size; ++i){
        add_elem(ret, cpy(lst->elems[i]), (eq_fct) equal_mat, (inc_fct) inc_mat);
    }
    return ret;
}

int eq_lst(MyList* lst1,MyList* lst2){
    return lst1==lst2;
}

void inc_lst(MyList* lst){
    if(lst==(MyList*)-1){ return; }
}