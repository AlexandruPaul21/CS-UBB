% replace(E1: element, E2: elemente, L: lista, Rez: lista)
% Modele de flux
% (i,i,i,0), (i,i,i,i) - deterministe
% E1 : elementul care se inlocuieste
% E2 : elementul cu care se inlocuieste
% L  : lista inainte de inlocuire
% Rez: lista dupa inlocuire
replace(_, _, [], []).
replace(E1, E2, [E1 | T], [E2 | T1]) :- replace(E1, E2, T, T1).
replace(E1, E2, [H | T], [H | T1]) :- 
                H \= E1
                ,replace(E1, E2, T, T1).

% max(L: lista, raspInt: intreg, raspFinal: intreg)
% Modele de flux
% (i, i, o), (i, i, i) - deterministe
% L : lista in care cautam maximul
% raspInt : raspuns intermediar, trebuie sa fie initial 0
% raspFinal : raspuns final, maximul sirului
max([], R, R).
max([H | T], Rez, R) :- is_list(H), !, max(T, Rez, R).
max([H | T], Rez, R) :- H >  Rez, max(T,   H, R).
max([H | T], Rez, R) :- H =< Rez, max(T, Rez, R).

% format(L: lista, MaxGlob: intreg, Rez: lista)
% Modele de flux
% (i,i,i), (i,i,o) - determinist
% L : lista initiala
% MaxGlob : maximul elemntelor care nu sunt lista din lista
% Rez : lista modificata
format([], _,[]) :- !.
format([H | T], Max, [H | T1]) :- not(is_list(H)), format(T, Max, T1).
format([H | T], Max, [H1 | T1]) :- is_list(H), max(H, 0, Rez), 
                                    replace(Max, Rez, H, H1),
                                    format(T, Max, T1).

% main(Lst: lista, Rez: lista)
% Modele de flux
% (i,o), (i,i) - determinist
% Lst : lista initiala
% Rez : lista finala, dupa aplicarea operatilor
main(Lst, Rez) :- max(Lst, 0, Max), format(Lst, Max, Rez).


