#pragma once

#include <vector>
#define MAX_CAP 10
#define NULL_TCHEIE 0

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

typedef struct{
    TElem elem;
    int left;
    int right;
    int parent;
}ABC_NODE;

class MDO {
	friend class IteratorMDO;
    private:
	/* aici e reprezentarea */
    int cp;
    int root;
    int firstFree;

    ABC_NODE* elems;

    Relatie comp;

    ABC_NODE aloca(TElem info);

    int tree_min(int node) const;

    void translate(int to,int from);

    void del_node(int index);

    int succesor(int index) const;

    void redim();

    //int prec(int index);

    public:

	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
    // Teta(1) - CF
    // Teta(h) - CM
    // Teta(h) - CD
    // O(h) - CT
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
    //Teta(1) - CF
    //Teta(h) - CD
    //Teta(h) - CM
    //O(h) - CT
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
    //Teta(1) - CF
    //Teta(h) - CD
    //Teta(h) - CM
    //O(h) - CT
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO
    //Teta(n) -CF=CD=CM=CT
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid
    //Teta(1)  CF=CD=CM=CT
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
    //Teta(1)  CF=CD=CM=CT
	IteratorMDO iterator() const;

	// destructorul 	
	~MDO();

    //functionalitatea noua
    //Teta(1) CF - radacina e maximul
    //Teta(h) CM,CD
    //O(h) CT
    TCheie cheieMaxima() const;

};
