dif([],_,[]).
dif([H | T1], [H | T2], Res) :- dif(T1, T2, Res).
dif([H | T1], [], [H | T2]) :- dif(T1, [], T2).
dif([H1 | T1], [H2 | T2], Res) :- H1 > H2, dif([H1 | T1], T2, Res).
dif([H1 | T1], [H2 | T2], [H1 | T3]) :- H1 < H2, dif(T1, [H2 | T2], T3).

add1([],[]).
add1([H | T1], [H    | T2]) :- 1 is (H mod 2),!, add1(T1,T2).
add1([H | T1], [H, 1 | T2]) :- add1(T1, T2).


%----------------------------------------------------------------------------------------------------


gcd(A,0,A).
gcd(A,B,C) :- B \= 0, D is (A mod B), gcd(B, D, C).

cmc([], Val, Val).
cmc([H | T], Val, Res) :- gcd(H,Val,R1),P is (Val * H),R is (P / R1), cmc(T, R, Res).

cmmc(Lst,Val) :- cmc(Lst,1,Val).

%----------------------------------------------------------------------------------------------------

check(E, [E | _]).
check(E, [H | T]) :- E \= H, check(E, T).

inv([], Rez, Rez).
inv([H | T], Rez, L) :- inv(T, [H | Rez], L).

get([], Rez, Res) :- inv(Rez, [], Res).
get([H | T], Rez, L) :- check(H, Rez), !, get(T, Rez, L).
get([H | T], Rez, L) :- get(T, [H | Rez], L).


%----------------------------------------------------------------------------------------------------

subst([], _, _, []).
subst([H | T1], E, L2, [H | T2]) :- H \= E, subst(T1, E, L2, T2).
subst([H | T1], H, L2, [L2 | T2]) :- subst(T1, H, L2, T2).

elim1([], _, []).
elim1([H | T1], M, [H | T2]) :- M \= 1, M1 is M-1, elim1(T1, M1, T2).
elim1([_ | T1], M, T2) :- M = 1, M1 is M-1, elim1(T1, M1, T2).

%----------------------------------------------------------------------------------------------------

count(_, [],0).
count(E, [E | T], Nr) :- count(E, T, Nr1), Nr is Nr1 + 1.
count(E, [H | T], Nr) :- H \= E, count(E, T, Nr).

elim([], _, []).
elim([H | T1], L2, [H | T2]) :- count(H, L2, 1),!, elim(T1, L2, T2).
elim([_ | T1], L2, L3) :- elim(T1, L2, L3).

elim_main(Lst, Rez) :- elim(Lst, Lst, Rez).

%----------------------------------------------------------------------------------------------------

reun([],[],[]).
reun([H | T], [], [H | T1]) :- reun(T, [], T1).
reun([], [H | T], [H | T1]) :- reun([], T, T1).
reun([H | T1], [H | T2], [H | T3]) :- reun(T1, T2, T3).
reun([H | T1], [K | T2], [H | T3]) :- H < K, reun(T1, [K | T2], T3).
reun([K | T1], [H | T2], [H | T3]) :- H < K, reun([K | T1], T2, T3).


%----------------------------------------------------------------------------------------------------

sum_main(L1, L2, Rez) :- reverse(L1,[], L1i), reverse(L2,[],L2i), sum(L1i, L2i, Rez1,0), reverse(Rez1,[],Rez).

sum([],[],[], 0) :- !.
sum([],[],[1],1) :- !.
sum([H1 | T1], [H2 | T2], [H3 | T3], CF1) :- Hp is H1 + H2 + CF1, CF is Hp div 10,
                                            H3 is Hp mod 10, sum(T1, T2, T3, CF).
sum([H1 | T1], [], [H3 | T3], CF1) :- Hp is H1 + CF1, CF is Hp div 10,
                                            H3 is Hp mod 10, sum(T1, [], T3, CF).
sum([], [H2 | T2], [H3 | T3], CF1) :- Hp is H2 + CF1, CF is Hp div 10,
                                            H3 is Hp mod 10, sum([], T2, T3, CF).

reverse([], Rez, Rez) :- !.
reverse([H | T1], Rez, Res) :- reverse(T1, [H | Rez], Res).


%----------------------------------------------------------------------------------------------------

get_max([],Max,Max).
get_max([H | T], Max, Maxg) :- H > Max, get_max(T, H, Maxg).
get_max([H | T], Max, Maxg) :- H =< Max, get_max(T,Max, Maxg).

get_pos([], _, _, []).
get_pos([H | T], Poz, H, [Poz | T1]) :- !, Poz1 is Poz + 1, get_pos(T, Poz1, H, T1).
get_pos([_ | T], Poz, E, Rez) :- Poz1 is Poz + 1, get_pos(T, Poz1, E, Rez).

main_4(L1, Rez) :- get_max(L1, -10000,Max), get_pos(L1, 1, Max, Rez).

%----------------------------------------------------------------------------------------------------

next_main(L1, Rez) :- reverse(L1, L1i),next(L1i, Rezi, 1), reverse(Rezi, Rez).

next([],[] ,0).
next([],[1],1).
next([H | T], [H1 | T1], CF1) :- Hp is H + CF1, CF is Hp div 10, H1 is Hp mod 10, next(T, T1, CF). 

%----------------------------------------------------------------------------------------------------

prec_main(L1, Rez) :- reverse(L1, L1i), prec(L1i, Rezi, 1), reverse(Rezi, Rez).

prec([], [], 0).
prec([1],[], 1) :- !.
prec([H | T], [H1 | T1], CF1) :- Hp is H - CF1, Hp < 0, H1 is 10+Hp, prec(T,T1,1).
prec([H | T], [H1 | T1], CF1) :- Hp is H - CF1, Hp >=0, H1 is Hp, prec(T,T1,0).

%----------------------------------------------------------------------------------------------------

mul_main(L1, E, Rez) :- reverse(L1, L1i), mul(L1i, E, 0, Rezi), reverse(Rezi, Rez).

mul([],_,0,[]) :- !.
mul([],_,CF,[CF]).
mul([H | T], E, CF1, [H1 | T1]) :- Hp is H * E + CF1, CF is Hp div 10, H1 is Hp mod 10, 
                                    mul(T, E, CF, T1).


%----------------------------------------------------------------------------------------------------

check_prim(N, D) :- N < D*D, !.
check_prim(N, D) :- not(0 is N mod D), check_prim(N, D+1).


comb([H|_],1,[H]).
comb([_|T],K,C) :- comb(T,K,C).
comb([H|T],K,[H|C]) :- K>1,
                       K1 is K-1,
                       comb(T,K1,C).

%----------------------------------------------------------------------------------------------------


dubl([],_,[]).
dubl([H | T], Last, [H | T1]) :- H \= Last, dubl(T, H, T1).
dubl([H | T], H, Rez) :- dubl(T, H, Rez).



%----------------------------------------------------------------------------------------------------

add_to(L1, L2, Rez) :- reverse(L1, L1i), insert(L1i,L2,Rez).

insert([],L2,L2) :- !.
insert([H | T], L2, Rez) :- insert(T, [H | L2], Rez).

inloc([], _, _, []).
inloc([H | T], H, L1, Rez) :- inloc(T, H, L1, Rez1), add_to(L1, Rez1, Rez).
inloc([H | T], E, L1, [H |T1]) :- H \= E, inloc(T, E, L1, T1).

%----------------------------------------------------------------------------------------------------


sterge([H1, H2], [H1, H2]) :- not( H1 is H2-1), !.
sterge([H1, H2], []) :- H1 is H2-1, !.
sterge([H], [H]) :- !.
sterge([],[]) :- !.
sterge([H1, H2 | T], [H1 | T1]) :- not(H1 is H2 -1 ), sterge([H2 | T], T1).
sterge([H1, H2, H3 | T], T1) :- H1 is H2 -1, H2 is H3 -1, sterge([H2, H3 | T], T1).
sterge([H1, H2, H3 | T], T1) :- H1 is H2 -1, not(H2 is H3 -1) , sterge([H3 | T], T1).

%----------------------------------------------------------------------------------------------------

main_max(L1, Rez) :- max_par(L1, 0,[],0,[],Rezi), reverse(Rez,[],Rezi).

max_par([],ALn,ALs,MLn, _, ALs) :- ALn>MLn, !.
max_par([], _, _, _, MLs, MLs).
max_par([H | T], ALn, ALs, MLn, MLs, Rez) :- 
                                0 is H mod 2,!, ALn1 is ALn +1,
                                ALs1 = [H | ALs], max_par(T, ALn1, ALs1, MLn, MLs, Rez).
max_par([_ | T], ALn, _, MLn, MLs, Rez) :- 
                                ALn =< MLn,!, 
                                max_par(T, 0, [], MLn, MLs, Rez).
max_par([_ | T], ALn, ALs, _, _, Rez) :-
                                max_par(T, 0, [], ALn, ALs, Rez).




