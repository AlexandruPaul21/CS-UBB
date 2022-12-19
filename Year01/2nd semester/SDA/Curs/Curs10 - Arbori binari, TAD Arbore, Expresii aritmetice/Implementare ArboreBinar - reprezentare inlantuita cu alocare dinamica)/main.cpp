#include "AB.h"
#include <iostream>

using namespace std;

AB creeazaArbore() {
    //arborele este
    //      1
    //       \
    //        3
    //       / \
    //      4   2
    AB ab, a2, a3, a4;
    ab.creeazaFrunza(1);
    a2.creeazaFrunza(2);
    a4.creeazaFrunza(4);
    a3.creeazaAB(a4,3,a2);
    ab.adaugaSubDr(a3);
    return ab;
}

//  ar trebui folosit iteratorul pentru tiparire
void tiparesteABPreordine(AB ab) {
   if (!ab.vid()){
    cout<<ab.element()<<" ";
    tiparesteABPreordine(ab.stang());
    tiparesteABPreordine(ab.drept());
   }
}

void tipar(TElem e){
  cout<<e<<" ";
}

int main() {
	AB ab=creeazaArbore();
	tiparesteABPreordine(ab);
	cout<<endl;
	ab.visitTree(&tipar);
	return 0;
}
