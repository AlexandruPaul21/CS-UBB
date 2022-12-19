
#include "colectie.h"
#include <iostream>

using namespace std;

void creeazaColectie(Colectie& c) {
	c.adauga(2);
	c.adauga(4);
	c.adauga(5);
	c.adauga(2);
	c.adauga(1);
}

void tiparesteColectie(const Colectie& c) {
	Iterator it = c.iterator();
	while (it.valid()) {
        TElement e = it.element();
		cout <<e<<" "<<endl;
		it.urmator();
	}
}

int main() {
	Colectie c;
	creeazaColectie(c);
	tiparesteColectie(c);
	return 0;
}
