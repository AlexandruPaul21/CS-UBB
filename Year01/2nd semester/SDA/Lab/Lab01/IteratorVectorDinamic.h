#pragma once
#include "VectorDinamic.h"

class IteratorVectorDinamic {

	friend class VectorDinamic;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container

	IteratorVectorDinamic(const VectorDinamic&);

	//contine o referinta catre containerul pe care il itereaza
	const VectorDinamic& v;

	/* aici e reprezentarea specifica a iteratorului */
    int* act;
    int poz;

public:

		//reseteaza pozitia iteratorului la inceputul containerului
        //Teta(1) CF=CM=CD=CT
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
        //Teta(1) CF=CM=CD=CT
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
        //Teta(1) CF=CM=CD=CT
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
        //Teta(1) CF=CM=CD=CT
		TElem element() const;

};