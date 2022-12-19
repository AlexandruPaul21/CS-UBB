//
// Created by Alexandru-Paul Sirbu on 23.03.2022.
//
#include <stdlib.h>
#include <string.h>
#include "domain.h"

Mat* create_mat(char* name, char* prod, int q){
    Mat* new_m= malloc(sizeof(Mat));
    int nrC;

    nrC=(int)strlen(name)+1;
    new_m->name= malloc(nrC*sizeof(char));
    strcpy(new_m->name,name);

    nrC=(int)strlen(prod)+1;
    new_m->prod= malloc(nrC*sizeof(char));
    strcpy(new_m->prod,prod);

    new_m->q=q;

    return new_m;
}

int validate(char* name, char* prod, int q){
    if(strlen(name)<=0) return 0;
    if(strlen(prod)<=0) return 0;
    if(q<=0) return 0;
    return 1;
}

int equal_mat(Mat* a,Mat* b){
    if(strcmp(a->name,b->name)==0 && strcmp(a->prod,b->prod)==0){
        return 1;
    }
    return 0;
}

void inc_mat(Mat* a,Mat* b){
    a->q+=b->q;
}

void destroy_mat(Mat* m){
    free(m->name);
    free(m->prod);
    free(m);
}

Mat* copy_mat(Mat* a){
    return create_mat(a->name,a->prod,a->q);
}

int filter_fct(Mat* mat,char* crt, char* val){
    if(strcmp(crt,"nume")==0){
        if(strlen(val)>1) return 0;
        return mat->name[0]==val[0];
    } else if(strcmp(crt,"producator")==0){
        return !strcmp(mat->prod,val);
    } else if(strcmp(crt,"cantitate")==0){
        int nr=0;
        for(int i=0; i<(int)strlen(val); ++i){
            if('0'<=val[i] && val[i]<='9'){
                nr=nr*10+(val[i]-'0');
            } else {
                return 0;
            }
        }
        return mat->q<=nr;
    }
    return 0;
}

int compName(Mat* a,Mat* b){
    return strcmp(a->name,b->name)>0;
}

int compProd(Mat* a,Mat* b){
    return strcmp(a->prod,b->prod)>0;
}

int compQ(Mat* a,Mat* b){
    return a->q>b->q;
}

int compComb(Mat* a,Mat* b){
    if(compName(a,b)==1 && compProd(a,b)==1 && compQ(a,b)==1){
        return 1;
    }
    return 0;
}