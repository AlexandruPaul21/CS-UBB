#include <stdio.h>
#include <assert.h>

/**
 * Determina toate reprezentarile posibile a unui numar natural ca suma de numere naturale consecutive.
*/

int suma(int start,int nr){
    //Programul verfica daca nr se poate scrie ca suma de nr consecutive incepand de la start
    //Returneaza 1 in caz afirmativ, 0 in caz contrar
    int s=0;
    while(s<nr){
        s+=start;
        ++start;
    }
    if(s==nr){
        return 1;
    } else {
        return 0;
    }
}

void show(int start,int nr){
    //subprogramul afiseaza o solutie gasita
    int s=0;
    while(s<nr){
        printf("%d ",start);
        s+=start;
        ++start;
    }
    printf("\n");
}

void solve(int nr){
    //subprogramul rezolva problema
    for(int start=1; start<=nr/2+1; ++start){
        if(suma(start,nr)){
            show(start,nr);
        }
    }
}

void impartire(int n, int a, int b){
	printf("0.");
	for (int i=1; i<=n; i++){
		//a / b
		int rest=a%b;
		rest*=10;
		a=rest;
		printf("%d",a/b);
	}
}

void test_suma(){
    assert(suma(9,69)==1);
    assert(suma(22,69)==1);
    assert(suma(23,69)==0);
    printf("All test passed!\n");
}

int main() {
    printf("Selectatic pb ce doriti a fi rezolvata:");
    int op;
    scanf("%d",&op);
    if(op==1) {
        printf("Introduceti numarul: ");
        int nr;
        scanf("%d", &nr);
        //test_suma();
        solve(nr);
    } else {
        int n,k,m;
        printf("Introduceti n:");
        scanf("%d",&n);
        printf("Introduceti k:");
        scanf("%d",&k);
        printf("Introduceti m:");
        scanf("%d",&m);
        impartire(n,k,m);

    }
    return 0;
}
