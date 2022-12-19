#include "TestScurt.h"
#include <assert.h>
#include <iostream>
#include "Dictionar.h"
#include "IteratorDictionar.h"

using namespace std;

void testAll() { //apelam fiecare functie sa vedem daca exista
	Dictionar d;
	assert(d.vid() == true);
	assert(d.dim() == 0); //adaug niste elemente
	assert(d.adauga(5,5)==NULL_TVALOARE);
	assert(d.adauga(1,111)==NULL_TVALOARE);
	assert(d.adauga(10,110)==NULL_TVALOARE);
	assert(d.adauga(7,7)==NULL_TVALOARE);
	assert(d.adauga(1,1)==111);
	assert(d.adauga(10,10)==110);
	assert(d.adauga(-3,-3)==NULL_TVALOARE);
	assert(d.dim() == 5);
	assert(d.cauta(10) == 10);
	assert(d.cauta(16) == -1);
	assert(d.sterge(1) == 1);
	assert(d.sterge(6) == -1);
	assert(d.dim() == 4);

	TElem e;
	IteratorDictionar id = d.iterator();
	id.prim();
	int s1 = 0, s2 = 0;
	while (id.valid()) {
		e = id.element();
		s1 += e.first;
		s2 += e.second;
		id.urmator();
	}
	assert(s1 == 19);
	assert(s2 == 19);

    //test func noua
    Dictionar d1,d2;
    for(int i=0; i<10; ++i){
        d1.adauga(i,2*i);
    }
    for(int i=0; i<10; ++i){
        d2.adauga(2*i,2*i);
    }
    d1.adaugaInexistene(d2);
    IteratorDictionar it=d1.iterator();
    for(int i=0; i<10; ++i){
        assert(it.element().first==it.element().second*2);
    }
    for(int i=10; i<=18; ++i){
        assert(it.element().first==it.element().second);
    }
}

