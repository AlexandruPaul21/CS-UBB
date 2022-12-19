#pragma once
#include "Lista.h"

class IteratorLP{

private:
    friend class Lista;
	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container

	IteratorLP(Lista& lista);

	//contine o referinta catre containerul pe care il itereaza
	Lista& lista;

	/* aici e reprezentarea  specifica a iteratorului */
    int act;
	
public:

        //Teta(1) CF - indica spre primul element
        //Teta(n) CD - indica spre ultimul element
        //Teta(n) CM
        //O(n)    CT
        TElem elimina();

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
        //Teta(1) CF
        //Teta(n) CD
        //Teta(n) CM
        //O(n)    CT
		TElem element() const;

};


