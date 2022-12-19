#include "lista.h"
#include <iostream>

int main()
{
    Lista l;
    l=creare();
    //tipar(l);
    std::cout << sumaPare(l._prim,1) - sumaImpare(l._prim,1) << "\n";

    Lista l2;
    l2 = creare();
    diff(l._prim,l2._prim);
}
