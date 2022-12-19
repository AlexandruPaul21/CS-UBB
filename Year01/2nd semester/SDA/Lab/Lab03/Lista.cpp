
#include <exception>

#include "IteratorLP.h"
#include "Lista.h"

#include <iostream>

using namespace std;

Lista::Lista() {
	/* de adaugat */
    cp=10;
    used=0;
    e=new TElem[cp];
    urm=new int[cp];
    prm=0;
    primLiber=1;
    for(int i=1; i<cp-1; ++i){
        urm[i]=i+1;
    }
    urm[cp-1]=0;
}

int Lista::dim() const {
	/* de adaugat */
    int start=prm;
    int sze=0;
    while(start!=0){
        ++sze;
        start=urm[start];
    }
	return sze;
}


bool Lista::vida() const {
	/* de adaugat */
    if(prm==0){
        return true;
    }
	return false;
}

IteratorLP Lista::prim() {
	/* de adaugat */
    return IteratorLP(*this);
}

TElem Lista::element(IteratorLP poz) const {
	/* de adaugat */
	return poz.element();
}

TElem Lista::sterge(IteratorLP& poz) {
	/* de adaugat */
    if(prm==0){
        throw exception();
    }
    if(poz.act==1){
        int kill=prm;
        int ret=e[kill];
        prm=urm[prm];
        urm[kill]=primLiber;
        primLiber=kill;
        return ret;
    }
	int head=prm;
    for(int pos=1; head!=0; ++pos){
        if(pos==poz.act-1){
            int ret=e[urm[head]];
            int kill=urm[head];
            urm[head]=urm[urm[head]];
            urm[kill]=primLiber;
            primLiber=kill;
            return ret;
        }
        head=urm[head];
    }
    throw exception();

}

IteratorLP Lista::cauta(TElem el) {
	/* de adaugat */
    int head=prm;
    for(int i=1;head!=0; ++i){
        if(e[head]==el){
            auto it= IteratorLP(*this);
            int j=1;
            while(j<i){
                it.urmator();
                ++j;
            }
            return it;
        }
        head=urm[head];
    }
	auto it=IteratorLP(*this);
    while(it.valid()){
        it.urmator();
    }
    return it;
}

TElem Lista::modifica(IteratorLP poz, TElem el) {
	/* de adaugat */
    int head=prm;
    for(int pos=1; head!=0; ++pos){
        if(poz.act==pos){
            TElem ret=e[head];
            e[head]=el;
            return ret;
        }
        head=urm[head];
    }
    throw exception();
}

void Lista::adauga(IteratorLP& poz, TElem el) {
	/* de adaugat */
    if(!poz.valid()){
        throw exception();
    }
    if(used==cp-2){
        redim();
    }
    int head=prm;
    for(int pos=1; head!=0; ++pos){
        if(poz.act-1==pos){
            e[primLiber]=el;
            urm[primLiber]=urm[urm[head]];
            urm[head]=primLiber;
        }
        head=urm[head];
    }
    ++used;
}

void Lista::adaugaInceput(TElem el) {
	/* de adaugat */
    if(used==cp-2){
        redim();
    }
    urm[primLiber]=prm;
    e[primLiber]=el;
    prm=primLiber;
    primLiber=urm[primLiber];
    ++used;
}

void Lista::adaugaSfarsit(TElem el) {
	/* de adaugat */
    if(used==cp-2){
        redim();
    }
    int head=prm;
    if(head==0){
        prm=1;
        e[prm]=el;
        primLiber=urm[primLiber];
        urm[prm]=0;
        return;
    }
    while(urm[head]!=0){
        head=urm[head];
    }
    urm[head]=primLiber;
    primLiber=urm[primLiber];
    e[urm[head]]=el;
    urm[urm[head]]=0;
    ++used;
}

Lista::~Lista() {
	/* de adaugat */
    delete[] e;
    delete[] urm;
}

void Lista::redim() {
    cp*=2;
    TElem* temp1=new TElem[cp];
    int* temp2=new int[cp];
    for(int i=1; i<=cp; ++i){
        temp1[i]=e[i];
        temp2[i]=urm[i];
    }
    primLiber=cp/2;
    for(int i=cp/2; i<cp; ++i){
        temp2[i]=i+1;
    }
    urm[cp-1]=0;
    delete[] e;
    delete[] urm;
    e=temp1;
    urm=temp2;
}
