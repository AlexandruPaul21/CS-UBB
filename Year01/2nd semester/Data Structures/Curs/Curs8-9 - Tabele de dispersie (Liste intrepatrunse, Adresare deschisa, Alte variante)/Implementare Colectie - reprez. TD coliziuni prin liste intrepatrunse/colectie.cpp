#include "colectie.h"
#include <iostream>
#include <math.h>

//functia care da <hashCode>-ul unui element
int hashCode(TElement e){
//ptr moment numarul e intreg
   return abs(e);
}

//functia de dispersie
int Colectie::d(TElement e) {
	//dispersia prin diviziune
	return hashCode(e) % m;
}


Colectie::Colectie() {
    m = MAX; //initializam m cu o valoare predefinita
	//daca va fi cazul, se poate redimensiona tabela si sa se redisperseze elementele
	//se initializeaza vectorii
	for (int i = 0; i < m; i++) {
		e[i] = -1; // consideram ca elementul e intreg
		urm[i] = -1;
	}
    //initializare primLIber
	primLiber = 0;
}

// actualizare primLiber
void Colectie::actPrimLiber()
{
	primLiber++;
	while (primLiber < m && e[primLiber] != -1)
		primLiber++;
}


//adaugare in Colectie
void Colectie::adauga(TElement elem) {
	//locatia de dispersie a cheii
	int i=d(elem);
	if (e[i] == -1)	// pozitia este libera
	{
		e[i] = elem;	// adaugam elementul
		if (i == primLiber)
			actPrimLiber();	// actualizam primLiber, daca este nevoie
		return;
	}

	int j = -1;	// j va retine pozitia precedenta lui i, pentru a putea reface inlantuirea
	// daca pozitia nu este libera
	while (i != -1)	// iteram pana gasim capatul inlantuirii
	{
		j = i;
		i = urm[i];
	}

	if (primLiber >= m)
		throw std::exception{ "Nu mai exista spatiu pentru adaugarea elementului!" }; //nu tratam depasirea tabelei

	// adaugam elementul
	e[primLiber] = elem;
	urm[j] = primLiber;
	actPrimLiber();

}

Iterator Colectie::iterator() const{
	return Iterator(*this);
}

Colectie::~Colectie() {
}

void Iterator::deplasare() {
	while ((curent < c.m) && c.e[curent] == -1)
		curent++;
}

//constructor iterator
Iterator::Iterator(const Colectie& col) :
		c(col) {
	curent = 0;
    
	deplasare();
}

void Iterator::prim() {
	//se determina prima lista nevida
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

