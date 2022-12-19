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
		Nod(TElem e, PNod st, PNod dr);
		TElem element();
		PNod stanga();		PNod dreapta();
	private:
		TElem e;
		PNod st, dr;
};

//clasa care defineste reprezentarea si functiile TAD Colectie
//pp. relatia de ordine intre elemente este <=
class Colectie {

private:
//rad este adresa radacinii
	PNod rad;

	PNod adauga_rec(PNod p, TElem e);

	void distrug_rec(PNod);

public:

//clasa Iterator trebuie sa fie prietena cu clasa Colectie
//pentru a putea avea acces la elementele ei private
	friend class Iterator;

//constructorul implicit al unei colectii
	Colectie();

//adauga un element de tipul TElem in colectie
	void adauga(TElem elem);

//alte operatii pe colectie...constructor copiere, cauta, sterge, dimensiune, iterator etc
// destructorul colectiei
	~Colectie();

};

class Iterator {
	//de adaugat
};

