
#include "Colectie.h"
#include <iostream>

using namespace std;

void creeazaColectie(Colectie& c) {
	c.adauga(2);
	c.adauga(4);
	c.adauga(2);
	c.adauga(5);
	c.adauga(4);
}

void tiparesteColectie(Colectie& c) {
	Iterator it = c.iterator();
	while (it.valid()) {
		cout << it.element() << ' ';
		it.urmator();
	}
}

int main() {
	Colectie c;
    creeazaColectie(c);
	tiparesteColectie(c);
	return 0;
}
