#pragma once
#include "Dictionar.h"

class IteratorDictionar
{
	friend class Dictionar;
private:

    	//constructorul primeste o referinta catre Container
    	//iteratorul va referi primul element din container
        //Teta(1) CF=CD=CM=CT
	IteratorDictionar(const Dictionar& d);

	//contine o referinta catre containerul pe care il itereaza
	const Dictionar& dict;
    int poz;
    Nod* act;
	/* aici e reprezentarea specifica a iteratorului */

public:

		//reseteaza pozitia iteratorului la inceputul containerului
        //Teta(1) CF=CD=CM=CT
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
        //Teta(1) CF=CD=CM=CT
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
        //Teta(1) CF=CD=CM=CT
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
        //Teta(1) CF=CD=CM=CT
		TElem element() const;
};
