#pragma once;

//tip de data generic (pentru moment este intreg)
typedef int TElem;

//capacitatea vectorului intern (vectorul e static)
#define CAPACITATE 100

//referire a clasei Iterator necesara folosirii instantelor de tip Iterator
//in definitia clasei Colectie inainte de definitia propriu-zisa a clasei Iterator
class Iterator;

//clasa care defineste reprezentarea si functiile TAD Colectie
class Colectie {
private:
    static const int cp=CAPACITATE;
    //vectorul static de elemente de tip TElem (cu spatiu fix de memorare - CAPACITATE)
	TElem e[cp];
	//vectorul static pentru egaturi
	int urm[cp];
	//referinta catre primul element al listei
	int prim;
	//referinta catre primul element din lista spatiului liber
	int primLiber;

    //functii pentru alocarea/dealocarea unui spatiu liber
    //se returneaza pozitia unui spatiu liber in lista
    int aloca();
    //dealoca spatiul de indice i
    void dealoca(int i);
    //functie privata care creeaza un nod in lista inlantuita
    int creeazaNod(TElem e);
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
	~Colectie(){};

 };



