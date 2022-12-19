#include "colectie.h"
#include <iostream>
#include <math.h>

//functioa care da hashCode-ul unuei element
int hashCode(TElement e){
//ptr moment numarul e intreg
   return abs(e);
}

Nod::Nod(TElement e, PNod urm) {
	this->e = e;
	this->urm = urm;
}

int Colectie::d(TElement e){
	//dispersia prin diviziune
	return hashCode(e) % m;
}


Colectie::Colectie() {
    m=MAX; //initializam m cu o valoare predefinita
	//daca va fi cazul, se poate redimensiona tabela si sa se redisperseze elementele
	//se initializeaza listele inlantuite ca fiind vide
	for (int i = 0;i < m;i++)
        	l[i] = nullptr;
}

//adaugare in Colectie
void Colectie::adauga(TElement e) {
	//locatia de dispersie a cheii
	int i=d(e);
	//se creeaza un nod
	PNod p = new Nod(e, nullptr);
	//se adauga in capul listei inlantuite de la locatia i
 	p->urm = l[i];
    	l[i]=p;
}

Iterator Colectie::iterator() const{
	return Iterator(*this);
}

Colectie::~Colectie() {
	//se elibereaza memoria alocata listelor
	for (int i=0;i<m;i++){
   	 //se elibereaza memoria pentru lista i
     	 while (l[i] != nullptr) {
		PNod p = l[i];
		l[i] = l[i]->urm;
		delete p;
	  }
    }
}

void Iterator::deplasare(){
//gaseste prima lista nevida incepand cu locatia poz din tabela
      while (poz<c.m && c.l[poz]==nullptr) poz++;
      if (poz<c.m)
	    curent=c.l[poz];
}

//constructor iterator
Iterator::Iterator(const Colectie& col) :
		c(col) {
	poz=0;
    
	deplasare();
}

void Iterator::prim() {
	//se determina prima lista nevida
	poz=0;
	deplasare();
}

void Iterator::urmator() {
   curent=curent->urm;
   if (curent==nullptr){
	poz=poz+1;
	deplasare();
   }
}

bool Iterator::valid() const {
	return (poz<c.m) && (curent != nullptr);
}

TElement Iterator::element() const {
	return curent->e;
}
