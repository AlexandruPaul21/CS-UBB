#include "ab.h"
#include <iostream>

Nod::Nod(TElem e, PNod st, PNod dr) {
	this->e = e;
	this->st = st;
	this->dr = dr;
}

AB::AB() {
	this->rad = NULL;
}

AB::AB(const AB& ab) {
	this->rad = copiere(ab.rad);
}

void AB::creeazaFrunza(TElem e){
    this->rad=new Nod(e, NULL, NULL);
}

void AB::creeazaAB(const AB& st, TElem e, const AB& dr){
    //distrug vechiul arbore
    distruge(this->rad);
    //creez radacina
    this->rad=new Nod(e, NULL, NULL);
    //copiez subarborii
    this->rad->st=copiere(st.rad);
    this->rad->dr=copiere(dr.rad);
}

PNod AB::copiere(PNod p) const{
  if (p!=NULL){
     //creez radacina
     PNod pNew=new Nod(p->e,NULL,NULL);
     pNew->st=copiere(p->st);
     pNew->dr=copiere(p->dr);
     return pNew;
  }
  return NULL;
}

void AB::distrugeSubarbori(PNod p){
  if (p!=NULL){
    distruge(p->st);
    distruge(p->dr);
  }
}

void AB::adaugaSubSt(const AB& st){
    //distrug vechii subarbori ai subarborelui stang
    distrugeSubarbori(this->rad->st);
    //copiez noul subarbore
    this->rad->st=copiere(st.rad);
}

void AB::adaugaSubDr(const AB& dr){
    //distrug vechii subarbori ai subarborelui drept
    distrugeSubarbori(this->rad->dr);
    //copiez noul subarbore
    this->rad->dr=copiere(dr.rad);
}

TElem AB::element() const{
   return this->rad->e;
}

AB AB::stang() const{
   AB ab;
   ab.rad=copiere(rad->st);
   return ab;
}

AB AB::drept() const{
   AB ab;
   ab.rad=copiere(rad->dr);
   return ab;
}

AB::~AB() {
	distruge(rad);
}

bool AB::vid() const{
  if (this->rad==NULL)
    return true;
  return false;
}

void AB::distruge(PNod p){
  if (p!=NULL){
    distruge(p->st);
    distruge(p->dr);
    delete p;
  }
}

void AB::visit(PNod p, PFunctie f){
   if (p!=NULL){
     f(p->e);
     visit(p->st, f);
     visit(p->dr, f);
   }
}

void AB::visitTree(PFunctie f){
   visit(rad, f);
}
