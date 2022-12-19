#include <stdio.h>
#include <stdlib.h>

int v[50];
int N[50];
int P[50];

int afisareASM(int n,int v[]);

int main() {
    FILE* f=fopen("numere.txt","r");

    int x;
    int ln=0;
    while(fscanf(f,"%d",&x)!=EOF){
        v[++ln]=x;
    }
    int n=0,p=0;
    for(int i=1; i<=ln; ++i){
        if(v[i]<0){
            N[++n]=v[i];
        } else {
            P[++p]=v[i];
        }
    }
    printf("Numere negative: ");
    afisareASM(n,N);
    printf("\n");
    printf("Numere pozitive: ");
    afisareASM(p,P);
    return 0;
}
