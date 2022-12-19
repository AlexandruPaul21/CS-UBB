#include "Iterator.h"
#include "LO.h"


Iterator::Iterator(const LO& l): lo(l){
	this->curent = l.prim;
}

void Iterator::prim(){
	this->curent = lo.prim;
}

void Iterator::urmator(){
 	this->curent=this->curent->urmator();
}

bool Iterator::valid() const{
	return (curent != nullptr);
}

TElem Iterator::element() const{
	return curent->element();
}


