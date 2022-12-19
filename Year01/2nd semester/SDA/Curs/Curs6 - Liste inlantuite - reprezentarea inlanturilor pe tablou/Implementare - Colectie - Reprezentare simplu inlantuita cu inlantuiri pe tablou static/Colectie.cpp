#include "Colectie.h"
#include "Iterator.h"
#include <iostream>


Colectie::Colectie() {
	//lista e vida
	prim = -1;
	//se initializeaza lista spatiului liber - toate pozitiile din vecto sunt marcate ca fiind libere
	for(int i=0;i<cp-1;i++)
        urm[i]=i+1;
        urm[cp-1]=-1;
	//referinta spre prima pozitie libera din lista
	primLiber = 0;
}

int Colectie::aloca() {
    //se sterge primul element din lista spatiului liber
    int i=primLiber;
    primLiber=urm[primLiber];
    return i;
}

void Colectie::dealoca(int i) {
    //se trece pozitia i in lista spatiului liber
    urm[i]=primLiber;
    primLiber=i;
}

//creeaza un nod in lista inlantuita unde se memoreaza informatia utila e
int Colectie::creeazaNod(TElem e){
    //daca s-ar foosi vector dinamic, s-ar redimensiona in cazul in care colectia ar fi plina (primLiber=0)
    int i=aloca();
    if(i!=-1) {//exista spatiu liber in lista
        this->e[i]=e;
        urm[i]=-1;
    }
    return i;
}

//adaugare la inceput
void Colectie::adauga(TElem e) {
	int i=creeazaNod(e);
    //in cazul folosirii unui vector static, e posibil ca i sa iasa -1 in cazul in care lista e plina
    if (i!=-1){
      urm[i]=prim;
      prim = i;
    }
}

Iterator Colectie::iterator() const {
	return Iterator(*this);
}

