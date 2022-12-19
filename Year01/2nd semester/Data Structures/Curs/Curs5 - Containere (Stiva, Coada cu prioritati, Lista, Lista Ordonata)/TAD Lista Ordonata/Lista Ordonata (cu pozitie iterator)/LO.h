#pragma once

class Iterator;

typedef int TComparabil;
typedef TComparabil TElem;

typedef bool (*Relatie)(TElem, TElem);


//referire a clasei Nod

class Nod;

//se defineste tipul PNod ca fiind adresa unui Nod

typedef Nod *PNod;

class Nod {
	public:

	    friend class LO;

        //constructor
		Nod(TElem e, PNod urm, PNod prec);
		TElem element();
		PNod urmator();
		PNod precedent();


	private:
		TElem e;
		//cele doua legaturi - lista  dublu inlantuita
		PNod urm, prec;

};

class LO {
private:
	friend class Iterator;
	//prim/ultim referas primul/ultimul nod din LDI

    PNod prim, ultim;

	//relatia de ordine intre elemente
	Relatie rel;

public:

	// constructor
	LO (Relatie r);

	// prima pozitie din lista
	Iterator primPoz() const;

	//cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
	Iterator cauta(TElem e) const;

	//adaugare element in LO a.i. sa se pastreze ordinea intre elemente
	void adauga(TElem e);

	//destructor
	~LO();

	// alte operatii specifice: constructor de copiere, etc

};
