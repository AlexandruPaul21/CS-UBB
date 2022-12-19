% add(L1 : lista, E : element, Rez : lista, G : flag/bool)
% Modele de flux: (i,i,i,i) - determinist, (i,i,o,i) - nedeterminist
% L1 - lista initiala
% E - elementul caruia trebuie sa ii modificam frecventa
% Rez - lista rezultat
% G - flag, care poate fi adevarat sau fals, in functie de daca am gasit sau nu valoarea
add([], _, [], 1).
add([], E, [[E,1]], 0).
add([ [ H | T ] | U], H,[[H , T1] | Rez], _) :- add(U, H, Rez, 1),
    T1 is T+1 .
add([ [ H | T ] | U], E,[[H | T ] | Rez], G) :- E \= H, add(U, E, Rez, G). 

% fr(L1: lista, Rezi : lista, Rezf : lista)
% Modele de flux: (i,i,i) - determinist, (i,i,o) - determinist
% L1 - lista initiala, din care vom deduce frecventa elementelor
% Rezi - variabila colectoare
% Rezf - lista finala, raspunsul problemei
fr([], Rez, Rez).
fr([H | T], Rez, Rezf) :- add(Rez, H, Rez1, 0),fr(T, Rez1, Rezf).

% main(L1 : lista, Rez : lista)
% Modele de flux: (i,i) - determinist, (i,o) - determinist
% L1 - lista initiala
% Rez - lista finala, cea cu perechi de forma, [element, frecventa]
main(L1, Rez) :- fr(L1, [], Rez).