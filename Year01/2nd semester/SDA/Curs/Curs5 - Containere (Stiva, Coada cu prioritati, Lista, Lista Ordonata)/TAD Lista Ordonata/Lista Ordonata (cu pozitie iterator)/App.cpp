#include <iostream>
#include "LO.h"
#include "Iterator.h"
#include "assert.h"

using namespace std;

bool maiMicSauEgal(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}


//creare lista
void creareLista (LO& lo){
 	lo.adauga(9);
 	lo.adauga(7);
	lo.adauga(5);
 	lo.adauga(1);
}

//tiparire folosind iteratorul
void tiparireLista(LO& lo){
	Iterator i = lo.primPoz();
	cout<<endl<<"Lista este:";
	while (i.valid()) {
		TElem e = i.element();
		cout<<e<<" ";
		i.urmator();
	}
 }

int main(){
    LO lo(maiMicSauEgal);
    creareLista(lo);
    //ar trebui sa tipareasca 1 5 7 9
    tiparireLista(lo);
    Iterator i=lo.cauta(0);
    assert(!i.valid()); 
	Iterator i1 = lo.cauta(10);
	assert(!i1.valid());
	Iterator i2 = lo.cauta(6);
	assert(!i2.valid());
	Iterator i3 = lo.cauta(5);
	assert(i3.element() == 5);
	cout << endl << "Gata";
    return 0;
}
