#include "colectie.h"
#include <iostream>
#include <math.h>

//constanta simbolica NIL indica o locatie libera in tabela
//consideram ca TElement este intreg
#define NIL -1 

//functia care da <hashCode>-ul unui element
int hashCode(TElement e){
//ptr moment numarul e intreg
   return abs(e);
}

//functia de dispersie extinsa - e verificare liniara
int Colectie::d(TElement e, int i) {
	return (hashCode(e) % m + i) % m;
}


Colectie::Colectie() {
    m = MAX; //initializam m cu o valoare predefinita
	//daca va fi cazul, se poate redimensiona tabela si sa se redisperseze elementele
	//se marcheaza pozitiile din tabela ca fiind libere
	for (int i = 0; i < m; i++) {
		e[i] = NIL; 
	}
}

//adaugare in Colectie
void Colectie::adauga(TElement elem) {
	//locatia de dispersie a cheii
	int i=0; //numarul de verificare
	bool gasit = false;
	do {
		int j = d(elem, i);
		if (e[j] == NIL) {
			e[j] = elem;
			gasit = true;
		}
		else i++;
	} while (i < m && !gasit);
	if (i == m) {
			//depasire tabela
			//ar fi necesara redimensionare
	}
}

Iterator Colectie::iterator() const{
	return Iterator(*this);
}

Colectie::~Colectie() {
}

void Iterator::deplasare() {
	while ((curent < c.m) && c.e[curent] == NIL)
		curent++;
}

//constructor iterator
Iterator::Iterator(const Colectie& col) :
		c(col) {
	curent = 0;
    
	deplasare();
}

void Iterator::prim() {
	//se determina prima locatie ocupata
	curent = 0;
	deplasare();
}

void Iterator::urmator() {
	curent++;
	deplasare(); 
}

bool Iterator::valid() const {
	return (curent < c.m);
}

TElement Iterator::element() const {
	return c.e[curent];
}

