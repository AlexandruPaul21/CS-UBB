candidat([H | _], H).
candidat([_ | T], Rez) :- candidat(T, Rez).

generate(L, S, Lc, Sc, Np, Lc) :- S = Sc, 0 is mod(Np, 2).
generate(L, S, [], 0, 0, Rez) :- candidat(L, L1),
                Np is 1 - mod(L1, 2),
                generate(L, S, [L1], L1, Np, Rez).

generate(L, S, [H | T], Sc, Np, Rez) :- candidat(L, A), A < H, Sc1 is Sc + A,
                Np1 is Np + 1 - mod(A, 2),
                generate(L, S, [A, H | T], Sc1, Np1, Rez).

generate_main(L, S, Rez) :- findall(Aux, generate(L, S, [], 0, 0, Aux), Rez).

candiB([H | _], H, []).
candiB([H | T1], C, [H | T2]) :- candiB(T1, C, T2).