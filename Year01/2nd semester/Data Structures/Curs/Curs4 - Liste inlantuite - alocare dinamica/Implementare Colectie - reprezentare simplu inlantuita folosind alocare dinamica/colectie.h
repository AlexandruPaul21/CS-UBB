#pragma once;

//tip de data generic (pentru moment este intreg)
typedef int TElem;

//referire a clasei Iterator necesara folosirii instantelor de tip Iterator
//in definitia clasei Colectie inainte de definitia propriu-zisa a clasei Iterator
class Iterator;

//referire a clasei Nod
class Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod *PNod;

class Nod
	{
	public:
	    friend class Colectie;
        //constructor
		Nod(TElem e, PNod urm);
		TElem element();
		PNod urmator();
	private:
		TElem e;
		PNod urm;
};

//clasa care defineste reprezentarea si functiile TAD Colectie
class Colectie {

private:
//prim este adresa primului Nod din lista
	PNod prim;

public:

//clasa Iterator trebuie sa fie prietena cu clasa Colectie
//pentru a putea avea acces la elementele ei private
	friend class Iterator;

//constructorul implicit al unei colectii
	Colectie();

//adauga un element de tipul TElem in colectie
	void adauga(TElem elem);

//alte operatii pe colectie...constructor copiere, cauta, sterge, dimensiune, etc

//expune un iterator pentru parcurgere unidirectionala a colectiei

	Iterator iterator() const;

// destructorul colectiei
	~Colectie();

};

//iterator unidirectional pe colectie
class Iterator {
private:
//pentru a construi un iterator pe o colectie este necesar ca un pointer sau referinta la aceasta sa ii fie oferit constructorului

	Iterator(const Colectie& col);

//contine o referinta catre colectia care se itereaza

	const Colectie& colectie;

//retine pozitia curenta in interiorul colectiei - adresa Nodului curent din lista asociata

	PNod curent;

public:

    friend class Colectie;

//reseteaza pozitia iteratorului la inceputul colectiei
	void prim();
//muta iteratorul pe urmatoarea pozitie in cadrul colectiei
	void urmator();
//verifica daca iteratorul e valid (indica un element al colectiei)
	bool valid() const;
//returneaza valoarea curenta a elementului din colectie
	TElem element() const;
};


