#include "lista.h"
#include <iostream>

using namespace std;


PNod creare_rec(){
  TElem x;
  cout<<"x=";
  cin>>x;
  if (x==0)
    return NULL;
  else{
    PNod p=new Nod();
    p->e=x;
    p->urm=creare_rec();
    return p;
  }
}

Lista creare(){
   Lista l;
   l._prim=creare_rec();
   return l;
}

void tipar_rec(PNod p){
   if (p!=NULL){
     cout<<p->e<<" ";
     tipar_rec(p->urm);
   }
}

void tipar(Lista l){
   tipar_rec(l._prim);
}

void distrug_rec(PNod p){
   if (p!=NULL){
     distrug_rec(p->urm);
     delete p;
   }
}

void distrug(Lista l) {
	//se elibereaza memoria alocata nodurilor listei
    distrug_rec(l._prim);
}

int sumaPare(PNod p, int poz) {
    if (p == NULL) {
        return 0;
    } else {
        if (poz % 2 == 0) {
            return p->e + sumaPare(p->urm, poz+1);
        }
        return sumaPare(p->urm, poz+1);
    }
}

int sumaImpare(PNod p, int poz) {
    if (p == NULL) {
        return 0;
    } else {
        if (poz % 2 != 0) {
            return p->e + sumaImpare(p->urm, poz+1);
        }
        return sumaImpare(p->urm, poz+1);
    }
}

void diff(PNod p1, PNod p2) {
    if (p1 == NULL || p2 == NULL) {
        if (p1 != NULL) {
            cout << p1->e << " ";
            diff(p1->urm, p2);
        } else if(p2 != NULL) {
            cout << p2->e << " ";
            diff(p1, p2->urm);
        }

        return;
    }
    if (p1->e == p2->e) {
        diff(p1->urm,p2->urm);
    } else if (p1->e < p2->e) {
        cout << p1->e << " ";
        diff(p1->urm, p2);
    } else {
        cout << p2->e << " ";
        diff(p1, p2->urm);
    }
}

