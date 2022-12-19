#include "Iterator.h"
#include "Colectie.h"


//constructor
Iterator::Iterator(const Colectie& col) :
	colectie(col) {
	curent = col.prim;
}

void Iterator::prim() {
	curent = colectie.prim;
}

void Iterator::urmator() {
	curent = colectie.urm[curent];
}

bool Iterator::valid() const {
	return curent != -1;
}

TElem Iterator::element() const {
	return colectie.e[curent];
}
