candidat([H | _], H).
candidat([_ | T], Rez) :- candidat(T, Rez).

generate(_, K, V, Lc, Ln, P, Lc) :- Ln = K, P < V.
generate(L, K, V, Lc, Ln, P, Rez) :- Ln < K, Ln1 is Ln + 1, P < V,
                                    candidat(L, L1), not(candidat(Lc, L1)),
                                    P1 is P * L1, 
                                    generate(L, K, V, [L1 | Lc], Ln1, P1, Rez).

generate_main(L, K, V, Rez) :- findall(Aux, generate(L, K, V, [], 0, 1, Aux), Rez).