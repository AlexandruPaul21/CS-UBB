#pragma once

typedef int TElem;

class IteratorLP;

class Lista {
private:
	friend class IteratorLP;
	/* aici e reprezentarea */
    int cp=0;
    int used=0;
    TElem* e;
    int* urm;
    int prm;
    int primLiber;
public:

		// constructor
		Lista ();

        //Teta(n) CF=CD=CM=CT
        void redim();

		// returnare dimensiune
        //Teta(n) CF=CD=CM=CT
		int dim() const;

		// verifica daca lista e vida
        //Teta(1) CF=CM=CD=CT
		bool vida() const;

		// prima pozitie din lista
        //Teta(1) CF=CM=CD=CT
		IteratorLP prim() ;

		// returnare element de pe pozitia curenta
		//arunca exceptie daca poz nu e valid
        //Teta(1) CF -elem pe prima pozitie
        //Teta(n) CD
        //Teta(n) CM
        //O(n)    CT
		TElem element(IteratorLP poz) const;

		// modifica element de pe pozitia poz si returneaza vechea valoare
		//arunca exceptie daca poz nu e valid
        //Teta(1) CF -elem pe prima pozitie
        //Teta(n) CD
        //Teta(n) CM
        //O(n)    CT
		TElem modifica(IteratorLP poz, TElem e);

		// adaugare element la inceput
        //Teta(1) amortizat
		void adaugaInceput(TElem e);		

		// adaugare element la sfarsit
        //Teta(n) CF=CD=CM=CT
		void adaugaSfarsit(TElem e);

		// adaugare element dupa o pozitie poz
		//dupa adaugare poz este pozitionat pe elementul adaugat
		//arunca exceptie daca poz nu e valid
        //complexitate amortizata
        //Teta(1) CF- elmentul trebuie adaugat pe prima poz
        //Teta(n) CD -ultima
        //Teta(n) CM
        //O(n)    CT
		void adauga(IteratorLP& poz, TElem e);

		// sterge element de pe o pozitie poz si returneaza elementul sters
		//dupa stergere poz este pozitionat pe elementul de dupa cel sters
		//arunca exceptia daca poz nu e valid
        //Teta(1) CF- elmentul trebuie sters de pe prima poz
        //Teta(n) CD -ultima
        //Teta(n) CM
        //O(n)    CT
		TElem sterge(IteratorLP& poz);

		// cauta element si returneaza prima pozitie pe care apare (sau iterator invalid)
        //Teta(1) CF- elmentul este pe prima poz
        //Teta(n) CD -ultima
        //Teta(n) CM
        //O(n)    CT
		IteratorLP cauta(TElem e);

		//destructor
		~Lista();
};
