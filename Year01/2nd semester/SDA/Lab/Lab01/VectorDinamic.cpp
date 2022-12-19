#include "VectorDinamic.h"
#include "IteratorVectorDinamic.h"
#include <stdlib.h>
#include <exception>
#include <iostream>

using namespace std;

void VectorDinamic::redim() {
	/* de adaugat */
    this->max_size*=2;
    //this->array=(TElem*)realloc(this->array, this->max_size*sizeof(TElem));
    TElem *temp=new int[this->max_size];
    for (int i = 0; i < this->max_size/2; i++){
        temp[i]=this->array[i];
    }
    delete[] this->array;
    this->array=temp;
}


VectorDinamic::VectorDinamic(int cp) {
	/* de adaugat */
    if(cp<=0){
        throw exception();
    }
    this->array=new int[cp];
    this->used=0;
    this->max_size=cp;
}


VectorDinamic::~VectorDinamic() {
	/* de adaugat */
    delete[] this->array;
}



int VectorDinamic::dim() const{
	/* de adaugat */
	return this->used;
}

TElem * VectorDinamic::get_arr() const{
    return this->array;
}

int VectorDinamic::get_used() const {
    return this->used;
}

TElem VectorDinamic::element(int i) const{
	/* de adaugat */
    if(i<0 || i>=this->used){
        throw exception();
    }
    return this->array[i];
}



void VectorDinamic::adaugaSfarsit(TElem e) {
	/* de adaugat */
    if(this->used==this->max_size-1){
        this->redim();
    }
    this->array[this->used]=e;
    this->used+=1;
}


void VectorDinamic::adauga(int i, TElem e) {
	/* de adaugat */
    if(this->used==this->max_size){
        this->redim();
    }
    for(int index=this->used; index>i; --index){
        this->array[index]=this->array[index-1];
    }
    this->array[i]=e;
    this->used+=1;
}


TElem VectorDinamic::modifica(int i, TElem e) {
	/* de adaugat */
    if(i<0 || i>=this->used){
        throw exception();
    }
    int elem=this->array[i];
    this->array[i]=e;
	return elem;
}


TElem VectorDinamic::sterge(int i) {
	/* de adaugat */
    if(i<0 || i>=this->used){
        throw exception();
    }
    int elem=this->array[i];
    //cout<<elem<<" "<<this->used<<"\n";
    for(int index=i; index<this->used-1; ++index){
        this->array[index]=this->array[index+1];
    }
    this->used-=1;
	return elem;
}

IteratorVectorDinamic VectorDinamic::iterator() {
	return IteratorVectorDinamic(*this);
}



