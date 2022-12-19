%Pb5

%a)
%sl(sir: lista, el: element ce trebuie sters, Rez: lista rezultat)
%(i,i,o) model de flux

sl([], _, []).
sl([H | T], H, Rez) :-
    sl(T, H, Rez).

sl([H | T], E, [H | Y]) :- E \= H,
    sl(T, E, Y).

%b)

%getFr(Sir: sirul de introdus, Rez: sirul rezultat)
getFr(Sir,Rez) :- fr(Sir,[],Rez).

%add(sir: lista data, E: elemnt ce trebuie adaugat, Rez:lista rezultat
%gasit: flag ce indica gasirea)
%(i, i, o, i) model de flux

add([], _, [], 1).
add([], E, [[E,1]], 0).
add([ [ H | T ] | U], E,[[H , T1] | Rez], _) :- E == H, add(U, E, Rez, 1),
    T1 is T+1 .
add([ [ H | T ] | U], E,[[H | T ] | Rez], G) :- E \= H, add(U, E, Rez, G). 

%fr(sir: sirul dat, Rez: sir intermediar, Rezf: rezultat final)
%(i,i,o) determinist
fr([], Rez, Rezf) :- copy(Rezf,Rez).
fr([H | T], Rez, Rezf) :- add(Rez, H, Rez1, 0),fr(T, Rez1, Rezf).

%copy(sir1: primul sir de copiat, sir2: sirul2 de copiat)
%(i,o) model de flux
copy([],[]).
copy([T | H], [T | H1]) :- copy(H, H1).
