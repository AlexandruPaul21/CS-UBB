#include "colectie.h"
#include <iostream>

Nod::Nod(TElem e, PNod urm) {
	this->e = e;
	this->urm = urm;
}

TElem Nod::element() {
	return e;
}

PNod Nod::urmator() {
	return urm;
}

Colectie::Colectie() {
	prim = nullptr;
}

//adaugare la inceput
void Colectie::adauga(TElem e) {
	PNod q = new Nod(e, nullptr);
	q->urm = prim;
	prim = q;
}

Iterator Colectie::iterator() const {
	return Iterator(*this);
}

Colectie::~Colectie() {
	//se elibereaza memoria alocata nodurilor listei
	while (prim != nullptr) {
		PNod p = prim;
		prim = prim->urm;
		delete p;
	}
}

//constructor
Iterator::Iterator(const Colectie& col) :
		colectie(col) {
	curent = col.prim;
}

void Iterator::prim() {
	curent = colectie.prim;
}

void Iterator::urmator() {
	curent = curent->urmator();
}

bool Iterator::valid() const {
	return curent != nullptr;
}

TElem Iterator::element() const {
	return curent->element();
}
