#include "IteratorDictionar.h"
#include "Dictionar.h"
#include <exception>

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d){
	/* de adaugat */
    poz=0;
    while(dict.comp[poz]== nullptr && poz<dict.m){
        ++poz;
    }
    if(poz==dict.m){
        act= nullptr;
    } else {
        act=dict.comp[poz];
    }
}


void IteratorDictionar::prim() {
	/* de adaugat */
    poz=0;
    while(dict.comp[poz]== nullptr && poz<dict.m){
        ++poz;
    }
    if(poz==dict.m){
        act= nullptr;
    } else {
        act=dict.comp[poz];
    }
}


void IteratorDictionar::urmator() {
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
    if(act->urm!= nullptr){
        act=act->urm;
    } else {
        ++poz;
        while(dict.comp[poz]== nullptr && poz<dict.m){
            ++poz;
        }
        if(poz==dict.m){
            act= nullptr;
            return;
        }
        act=dict.comp[poz];
    }
}


TElem IteratorDictionar::element() const{
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
    TElem ret= act->elem;
	return ret;
}


bool IteratorDictionar::valid() const {
	/* de adaugat */
    if(act!= nullptr){
        return true;
    }
	return false;
}

