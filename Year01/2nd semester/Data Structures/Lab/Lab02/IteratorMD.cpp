#include "IteratorMD.h"
#include "MD.h"
#include <exception>

using namespace std;

IteratorMD::IteratorMD(const MD& _md): md(_md) {
	/* de adaugat */
    act=md.start;
}

TElem IteratorMD::element() const{
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
	return pair <TCheie, TValoare>  (act->key.first,act->key.second);
}

bool IteratorMD::valid() const {
	/* de adaugat */
    if(act==NULL)
	    return false;
    return true;
}

void IteratorMD::urmator() {
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
    act=act->next;
}

void IteratorMD::prim() {
	/* de adaugat */
    act=md.start;
}

