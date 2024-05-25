candidat([H | _], H).
candidat([_| T1], C) :- candidat(T1, C).

sum([], 0).
sum([H | T], S) :- sum(T,S1), S is S1 + H.

check([], _).
check([H | T], E) :- E \= H, check(T,E).

generate(_, L2, N, L2) :- 0 is mod(N,2), sum(L2,S), 1 is mod(S,2).
generate(L, L2, N, A ) :- candidat(L, E), check(L2, E), N1 is N + 1,
                        generate(L, [E | L2], N1, A).

generate_main(L, A) :- findall(B, generate(L, [], 0, B),A).

