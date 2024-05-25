candidat([H | _], H).
candidat([_ | T], C) :- candidat(T, C).

gen(_, K, Lc, Ln, _, Lc) :- Ln = K.
gen(L, K, [], _, _, Rez) :- candidat(L, A), candidat(L, B),
                A < B, R is B - A, 
                gen(L, K, [A, B], 2, R, Rez).
gen(L, K, [H | T], Ln, R, Rez) :- candidat(L, A),
                Ln < K, Ln1 is Ln + 1, R is H - A,
                gen(L, K, [A,H|T], Ln1, R, Rez).

gen_main(L, K, Rez) :- findall(Aux, gen(L, K, [], 0, 0, Aux), Rez).