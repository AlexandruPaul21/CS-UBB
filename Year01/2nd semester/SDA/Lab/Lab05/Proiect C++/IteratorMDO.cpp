#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
	/* de adaugat */
    act=dict.tree_min(dict.root);
}

void IteratorMDO::prim(){
	/* de adaugat */
    act=dict.tree_min(dict.root);
}

void IteratorMDO::urmator(){
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
    act=dict.succesor(act);
}

bool IteratorMDO::valid() const{
	/* de adaugat */
	return act!=-1;
}

TElem IteratorMDO::element() const{
	/* de adaugat */
    if(!valid()){
        throw exception();
    }
	return dict.elems[act].elem;
}


