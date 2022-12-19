#ifndef AB_H
#define AB_H

//tip de data generic (pentru moment este intreg)
typedef int TElem;

//referire a clasei Nod
class Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod *PNod;

class Nod
	{
	public:
	   friend class AB;
        //constructor
		Nod(TElem e, PNod st, PNod dr);
	private:
		TElem e;
		PNod st,dr;
};

//tipul unui pointer spre o functie folosit pentru vizitarea nodurilor arborelui
typedef void (*PFunctie)(TElem);

//clasa care defineste reprezentarea si functiile TAD Arbore Binar
class AB {
private:
//rad este adresa radacinii arborelui
	PNod rad;
//functia recursiva pentru distrugerea arborelui
	void distruge (PNod);
//functia recursiva pentru distrugerea subarborilor unui arbore
	void distrugeSubarbori (PNod);
//copiere subarbore
    PNod copiere(PNod) const;

//prelucrare recursiva
    void visit(PNod, PFunctie);

public:
//constructorul implicit al unui arbore binat
	AB();
//constructor copiere
    AB(const AB&);
//creeaza un arbore binar care se reduce la o frunza
	void creeazaFrunza(TElem elem);
//constructor arbore binar
	void creeazaAB(const AB& st, TElem elem, const AB& dr);
//adauga unui arbore binar subarborele stang
	void adaugaSubSt(const AB& st);
//adauga unui arbore binar subarborele drept
	void adaugaSubDr(const AB& dr);
//verofica daca arborele este vid
    bool vid() const;
//accesare element din radacina
	TElem element() const;
//accesare subarbore stang
	AB stang() const;
//accesare subarbore drept
	AB drept() const;

//alte operatii pe arborele binar...iterator, cauta, sterge, etc


//prelucrarea nodurilor din arbore
    void visitTree(PFunctie);

// destructorul arborelui binar
	~AB();

};


#endif
