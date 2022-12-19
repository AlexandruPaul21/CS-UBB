#pragma once;

#define MAX 10 //numarul maxim de locatii din tabela

typedef int TElement;


//referire a clasei Iterator necesara folosirii instantelor de tip Iterator
class Iterator;

//referire a clasei Nod
class Nod;

//se defineste tipul PNod ca fiind adresa unui Nod dintr-o lista inlantuita
typedef Nod *PNod;

class Nod
	{
	private:
		TElement e;
		PNod urm;
	public:
	    friend class Colectie;
	    friend class Iterator;
        //constructor
	    Nod(TElement e, PNod urm);
	    //TElement element();
        //PNod urmator(); 
};

//clasa care defineste reprezentarea si functiile TAD Colectie

class Colectie{
private:
	//reprezentare folosind o TD - rezolvare coliziuni prin liste 
   	int m; //numarul de locatii din tabela de dispersie
	PNod l[MAX]; //listele independente - vector static

	//functia de dispersie
	int d(TElement e);

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
    
	void deplasare();

//contine o referinta catre colectia pe care o itereaza
	
	const Colectie& c;
//locatia curenta din tabela
	int poz;
//retine pozitia curenta in interiorul listei de la locatia curenta - adresa Nodului curent din lista asociata
    	PNod curent;

public:

    friend class Colectie;

//reseteaza pozitia iteratorului la inceputul colectiei
	void prim();

//muta iteratorul pe urmatoarea pozitie in cadrul colectiei
	void urmator();

//verifica daca iteratorul e valid (indica un element al colectiei
	bool valid() const;

//returneaza valoarea curenta a elementului din colectie
	TElement element() const;
};


