#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

using namespace std;

IteratorLP::IteratorLP(Lista& l):lista(l) {
	/* de adaugat */
    act=lista.prm;
}

void IteratorLP::prim(){
	/* de adaugat */
    act=lista.prm;
}

void IteratorLP::urmator(){
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
    act=lista.urm[act];
}

bool IteratorLP::valid() const{
	/* de adaugat */
	return act!=0;
}

TElem IteratorLP::element() const{
	/* de adaugat */
    if(!valid()){
        throw std::exception();
    }
    int head=lista.prm;
    for(int pos=1; pos<act && head!=0; ++pos){
        head=lista.urm[head];
    }
    if(head==0){
        throw exception();
    }
	return lista.e[head];
}

TElem IteratorLP::elimina() {
    if(!valid()){
        throw exception();
    }
    if(act==lista.prm){
        int kill=lista.prm;
        lista.prm=lista.urm[lista.prm];
        urmator();
        return lista.e[kill];
    }
    int head=lista.prm;
    for(int pos=1; pos<act-1 && head!=0; ++pos){
        head=lista.urm[head];
    }
    int kill=lista.urm[head];
    lista.urm[head]=lista.urm[lista.urm[head]];
    //urmator();
    return lista.e[kill];
}


