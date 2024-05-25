candidat([H | _], H, []).
candidat([H | T1], C, [H | T2]) :- candidat(T1, C, T2).

gen(L, K, Lc, Ln, S, Lc) :- Ln = K, 0 is mod(S,2).
gen(L, K, Lc, Ln, S, Rez) :- candidat(L, L1, Lp), Ln < K, Ln1 is Ln + 1, 
                S1 is S + L1, gen(Lp, K, [L1 | Lc], Ln1, S1, Rez).

gen_main(L, K, Rez) :- findall(Aux, gen(L, K, [], 0, 0, Aux), Rez).