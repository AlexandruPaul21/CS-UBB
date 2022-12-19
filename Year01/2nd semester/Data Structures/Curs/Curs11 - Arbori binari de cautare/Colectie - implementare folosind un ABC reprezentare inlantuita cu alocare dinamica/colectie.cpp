#include "colectie.h"
#include <iostream>

using namespace std;

Nod::Nod(TElem e, PNod st, PNod dr) {
	this->e = e;
	this->st = st;
	this->dr = dr;
}

TElem Nod::element() {
	return e;
}

PNod Nod::stanga() {
	return st;
}

PNod Nod::dreapta() {
	return dr;
}

Colectie::Colectie() {
	rad = nullptr;
}

//adaugare recursiva a unui element
PNod Colectie::adauga_rec(PNod p, TElem e) {
	if (p == nullptr) {
		p = new Nod(e, nullptr, nullptr);
	}
	else {
		if (e <= p->e) //se adauga in stanga
			p->st = adauga_rec(p->st, e);
		else //se adauga in dreapta
			p->dr = adauga_rec(p->dr, e);
	}
	return p;
}

//adaugare la inceput
void Colectie::adauga(TElem e) {
	rad = adauga_rec(rad, e);
}

void Colectie::distrug_rec(PNod p) {
	if (p != nullptr) {
		distrug_rec(p->stanga());
		distrug_rec(p->dreapta());
		delete p;
	}
}

Colectie::~Colectie() {
	//se elibereaza memoria alocata nodurilor listei
	distrug_rec(rad);
}
