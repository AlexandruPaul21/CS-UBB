
#include <exception>

#include "Iterator.h"
#include "LO.h"

#include <iostream>

using namespace std;

Nod::Nod(TElem e, PNod urm, PNod prec) {
	this->e = e;
	this->urm = urm;
	this->prec = prec;
}

TElem Nod::element() {
	return e;
}

PNod Nod::urmator() {
	return urm;
}

PNod Nod::precedent() {
	return prec;
}

LO::LO(Relatie r) {
	rel = r;
	this->prim = nullptr;
	this->ultim = nullptr;
}

Iterator LO::primPoz() const {
	Iterator it = Iterator(*this);
	it.curent = prim;
	return it;
}

/* nu e tratat decat cazul de adaugare la inceputul listei*/
void LO::adauga(TElem e){
	PNod nou = new Nod(e, nullptr, nullptr);
	//daca lista e vida, se adauga primul element
	if (prim == nullptr) {
		prim = nou;
		ultim = nou;
	}
	else {
		//elementul de adaugat e in relatie cu informatia utila a primului nod 
		//se adaauga ininte de prim
		nou->urm = prim;
		prim->prec = nou;
		prim = nou;
	}
	//de completat celelalte cazuri
}

Iterator LO::cauta(TElem e) const {
	Iterator it = Iterator(*this);
	//la inceput iteratorul e invalid
	it.curent = nullptr;
	//daca elementul sigur nu apare in lista (lista e vida, elementul e < primul sau e > ultim - daca relatia e <=) 
	if ((prim == nullptr) || (!rel(prim->e, e)) || (!rel(e, ultim->e)))
		return it;
	//cautam prima aparitie a elementului in lista
	PNod p = prim;
	//tinem cont ca elementele sunt in ordine, parcurgem cat timp elementul cautat e in relatie cu elementul listei   
	while ((p != nullptr) && (p->e != e) && (!rel(e, p->e))) {
			p = p->urm;
	}
	//am gasit elementul
	if ((p != nullptr) && (p->e == e)) {
		//setam iteratorul pe element
		it.curent = p;
	}	
	return it;
}

LO::~LO() {
    while (prim != nullptr){
        PNod p = prim;
        prim = prim->urm;
        delete p;
    }
}

