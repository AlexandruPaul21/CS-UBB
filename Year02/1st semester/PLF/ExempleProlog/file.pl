r(X) :- p(X), q(X).
p(X) :- w(X).
p(X) :- s(X).
s(X) :- t(X).
q(X) :- t(X).
t(a).
w(b).

%tata(X, Y) reprezentȃnd relația “Y este tatăl lui X”
%mama(X, Y) reprezentȃnd relația “Y este mama lui X”
mama(a,b).
mama(e,b).
tata(c,d).
tata(a,d).

%parinte(X, Y) reprezentȃnd relația “Y este părintele lui X”
%frate(X, Y) reprezentȃnd relația “Y este fratele lui X”
parinte(X,Y) :-tata(X,Y).
parinte(X,Y) :-mama(X,Y).

frate(X,Y) :- parinte(X,Z),
	parinte(Y,Z),
	X \= Y.

% fact1(N:integer, F:integer)
% (i, i) (i,o)

fact2(0, 1):-!.
fact2(N, F) :-
	N1 is N-1,
	fact2(N1, F1),
	F is N * F1.

