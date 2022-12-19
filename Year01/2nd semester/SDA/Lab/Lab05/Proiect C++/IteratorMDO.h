#pragma once

#include "MDO.h"


class IteratorMDO{
	friend class MDO;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	IteratorMDO(const MDO& dictionar);

	//contine o referinta catre containerul pe care il itereaza
	const MDO& dict;
	/* aici e reprezentarea  specifica a iteratorului */
    int act;

public:

		//reseteaza pozitia iteratorului la inceputul containerului
        //Teta(1) - CF
        //Teta(h) - CD
        //Teta(h) - CM
        //O(h) - CT
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
        //Teta(1) - CF
        //Teta(h) - CD
        //Teta(h) - CM
        //O(h) - CT
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
        //Teta(1) CF=CD=CM=CT
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
        //Teta(1) CF=CD=CM=CT
		TElem element() const;
};

