#pragma once

#define MAX 701
//857
#define NULL_TVALOARE -1
typedef int TCheie;
typedef int TValoare;

class IteratorDictionar;
class Dictionar;

#include <utility>
typedef std::pair<TCheie,TValoare> TElem;

class Nod{
private:
    TElem elem;
    Nod* urm;
public:
    friend class Dictionar;
    friend class IteratorDictionar;
    Nod(TElem elem,Nod* urm);
};

class Dictionar {
	friend class IteratorDictionar;

	private:
	/* aici e reprezentarea */
    int m;
    Nod* comp[MAX];

    int d(TCheie ch) const;

	public:

	// constructorul implicit al dictionarului
	Dictionar();

	// adauga o pereche (cheie, valoare) in dictionar	
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
    //Teta(1) CF=CD=CM=CT se adaugă la începtulu unei liste simplu înlănțuite
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
    //Teta(1) - CF - elementul se află în capul listei tabelei de dispersie
    //Teta(n) - CD - toate elementele se dispersează în aceeași locație/
    //Teta(1) - CM
    //Teta(1) - CT
	TValoare cauta(TCheie c) const;

	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
    //Teta(1) -CF - elementul ce trebuie sters e in capul listei
    //Teta(n) -CD - trebuie parcursă toată lista și toate elementele se dispersează în aceeași locație
    //Teta(1) -CM
    //Teta(1) - CT
	TValoare sterge(TCheie c);

	//returneaza numarul de perechi (cheie, valoare) din dictionar
    //Teta(n) CF=CD=CM=CT
	int dim() const;

	//verifica daca dictionarul e vid
    //Teta(1) CF=CD=CM=CT
	bool vid() const;

	// se returneaza iterator pe dictionar
    //Teta(1) CF=CD=CM=CT
	IteratorDictionar iterator() const;

    //adauga in dictionarul curent toate perechile din d a caror cheie nu se afla deja in dictionar
    //returneaza numarul de perechi adaugate
    //Teta(n) - CD - cand cautarea se realizeaza in Teta(n)
    //Teta(1) - CF
    //Teta(1) - CM
    //Teta(1) - CT
    int adaugaInexistene(Dictionar& d);

	// destructorul dictionarului
    //Teta(n) CF=CD=CM=CT
	~Dictionar();

};


