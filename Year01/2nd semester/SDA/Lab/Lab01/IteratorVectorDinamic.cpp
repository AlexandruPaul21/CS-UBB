#include "IteratorVectorDinamic.h"
#include "VectorDinamic.h"


IteratorVectorDinamic::IteratorVectorDinamic(const VectorDinamic& _v) :

		v(_v) {
	/* de adaugat */
    this->act=v.array;
    this->poz=0;

}



void IteratorVectorDinamic::prim() {
	/* de adaugat */
    this->act=v.get_arr();
    this->poz=0;
}



bool IteratorVectorDinamic::valid() const{
	/* de adaugat */
    if(this->poz<v.get_used()){
        return true;
    }
	return false;
}



TElem IteratorVectorDinamic::element() const{
	/* de adaugat */
	return *this->act;
}



void IteratorVectorDinamic::urmator() {
	/* de adaugat */
    this->act+=1;
    this->poz+=1;
}

