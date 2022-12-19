#pragma once
//tip de data generic (pentru moment este intreg)

//#include "IteratorVectorDinamic.h"

typedef int TElem;


class IteratorVectorDinamic;


class VectorDinamic {
 friend class IteratorVectorDinamic;
private:
    int max_size;
	// redimensionare
    // O(n)
	void redim();
    /* aici e reprezentarea */
    int *array;
    int used;

public:
		// constructor
		//arunca exceptie in cazul in care capacitate e <=0
        //Teta(1) CF=CD=CM=CT
		VectorDinamic (int capacitate);

		// returnare dimensiune
        //Teta(1) CF=CD=CM=CT
		int dim() const;

        //Teta(1) CF=CD=CM=CT
        TElem* get_arr() const;

        //Teta(1) CF=CD=CM=CT
        int get_used() const;

		// returnare element
		//arunca exceptie daca i nu e valid
		//indicii ii consideram de la 0
        //Teta(1) CF=CD=CM=CT
		TElem element(int i) const;

		// modifica element de pe pozitia i si returneaza vechea valoare
		//arunca exceptie daca i nu e valid
        //Teta(1) CF=CD=CM=CT
		TElem modifica(int i, TElem e);

		// adaugare element la sfarsit
        //Teta(1) CF + nu e nevoie de redmiensionare
        //Teta(n) CD e nevoie de redimensionare
        //Teta(n) CM
        //O(n) CT
		void adaugaSfarsit(TElem e);

		// adaugare element pe o pozitie i
		//arunca exceptie daca i nu e valid
        //Teta(1)  CF - i==n + nu e nevoie de redimensionare
        //Teta(n)  CD - i==0 + e nevoie de redimensionare
        //Teta(n)  CM
        //O(n)  CT
		void adauga(int i, TElem e);

		// sterge element de pe o pozitie i si returneaza elementul sters
		//arunca exceptie daca i nu e valid
        //Teta(1)  CF - i==n
        //Teta(n)  CD - i==0
        //Teta(n)  CM
        //O(n)  CT
		TElem sterge(int i);

		// returnare iterator
        //O(1) CF=CD=CM=CT
		IteratorVectorDinamic iterator();

		//destructor
        //Teta(n) CF=CD=CM=CT
		~VectorDinamic();
};







