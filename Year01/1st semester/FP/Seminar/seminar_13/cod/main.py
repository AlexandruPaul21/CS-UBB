#cod random

#divide & conquer
def exista_impare(lista):
    if len(lista)==1:
        if lista[0]%2==1:
            return 1
        else:
            return 0
    m=len(lista)//2
    return max(exista_impare(lista[m:]),exista_impare(lista[:m]))

def suma_pare(lista):
    if len(lista)==1:
        if lista[0]%2==0:
            return lista[0]
        else:
            return 0

    m=len(lista)//2
    return suma_pare(lista[m:])+suma_pare(lista[:m])

#backtracking

#b1
def back_rec(x,cuv):
    x.append(-1)
    for litera in cuv:
        x[-1]=litera
        if este_consistent(x):
            if este_solutie(x):
                afisare(x)
            else:
                back_rec(x[:],cuv)

    x.pop()

#b2
def back_iter(nr,binar):
    binar=[-1]
    while len(binar)>0:
        choosen=False
        while not choosen and binar[-1]<2:
            binar[-1]=binar[-1]+1
            choosen=consistent(nr,binar)
            if choosen:
                if solution(nr,binar):
                    solutionFound(nr,binar)
                binar.append(-1)
            else:
                binar=binar[:-1]
