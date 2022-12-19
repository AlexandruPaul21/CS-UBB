#pragma once;


#define MAX 20 //numarul maxim de locatii din tabela

typedef int TElement;

//referire a clasei Iterator necesara folosirii instantelor de tip Iterator
class Iterator;

//clasa care defineste reprezentarea si functiile TAD Colectie

class Colectie {
private:
	//reprezentare folosind o TD - rezolvare coliziuni prin adresare deschisa si verificare liniara
	int m; //numarul de locatii din tabela de dispersie
	TElement e[MAX]; //vectorul elementelor - vector static

	// functia de dispersie extinsa 
	int d(TElement e, int i);

public:
//clasa Iterator trebuie sa fie prietena cu clasa Colectie
//pentru a putea avea acces la elementele ei private
	friend class Iterator;

//constructorul implicit al unei colectii
	Colectie();

//adauga un element de tipul TElem in colectie
	void adauga(TElement elem);

//alte operatii pe colectie...constructor copiere, cauta, sterge, dimensiune, etc


//expune un iterator pentru parcurgere unidirectionala a colectiei

	Iterator iterator() const;

// destructorul colectiei
	~Colectie();

};

//iterator pe colectie
class Iterator {
private:
//pentru a construi un iterator pe o colectie este necesar ca un pointer sau referinta la aceasta sa ii fie oferit constructorului
	Iterator(const Colectie& col);
    
//contine o referinta catre colectia pe care o itereaza
	
	const Colectie& c;
//locatia curenta din tabela
	int curent;

//deplasare iterator pe o pozitie care e ocupata (nu are memorat NIL_
	void deplasare();

public:

    friend class Colectie;

//reseteaza pozitia iteratorului la inceputul colectiei
	void prim();

//muta iteratorul pe urmatoarea pozitie in cadrul colectiei
	void urmator();

//verifica daca iteratorul e valid (indica un element al colectiei)
	bool valid() const;

//returneaza valoarea curenta a elementului din colectie
	TElement element() const;
};


