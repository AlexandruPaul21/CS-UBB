candidat(V, N, V) :- V =< N.
candidat(V, N, Rez) :- V1 is V + 1, V < N, candidat(V1, N, Rez).

exists([E | _], E) :- !.
exists([H | T], E) :- H \= E, exists(T, E).

abs(X, X ) :- X >= 0.
abs(X, X1) :- X <  0, X1 is X * -1.

generate(N, P, Ln, P) :- N = Ln.
generate(N, [], 0, Rez) :- candidat(1, N, A), generate(N, [A], 1, Rez).
generate(N, [H | T], Ln, Rez) :- candidat(1,N, A),
                        DF is H - A,
                        abs(DF, DFA), DFA >= 2, Ln < N, Ln1 is Ln + 1,
                        not(exists([H | T], A)), generate(N, [A, H|T], Ln1, Rez).

generate_main(N, Rez) :- findall(Aux, generate(N, [], 0, Aux), Rez).