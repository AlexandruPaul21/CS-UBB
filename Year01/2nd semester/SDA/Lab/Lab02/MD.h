#pragma once
#include<vector>
#include<utility>
#include<string>
#define NULL_TCHEIE 0
using namespace std;

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

struct Node{
    TElem key;
    Node* next;
    Node* prev;
};

class IteratorMD;

class MD
{
	friend class IteratorMD;
private:
	/* aici e reprezentarea */
    Node* start;
public:

    //gaseste si returneaza cheia minima a multidictionarului
    //Daca multidictionarul este vid, se returneaza NULL_TCHEIE
    TCheie cheieMinima() const;

	// constructorul implicit al MultiDictionarului
    //Teta(1) CF=CD=CM=CT
	MD();

	// adauga o pereche (cheie, valoare) in MD
    //Teta(n) CF=CD=CM=CT
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
    //Teta(n) CF=CD=CM=CT
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
    //Teta(1) CF - elementul se afla pe prima pozitie
    //Teta(n) CD - elementul se afla pe ultima pozitie/nu se gaseste deloc
    //Teta(n) CM
    //O(n) - CT
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MD
    //Teta(n) CF=CD=CM=CT
	int dim() const;

	//verifica daca MultiDictionarul e vid
    //Teta(1) CF=CD=CM=CT
	bool vid() const;

	// se returneaza iterator pe MD
    //Teta(1) CF=CD=CM=CT
	IteratorMD iterator() const;

	// destructorul MultiDictionarului
    //Teta(n) CF=CD=CM=CT
	~MD();



};

