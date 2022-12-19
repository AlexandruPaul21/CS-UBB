bubble_sort(List,Sorted):-b_sort(List,[],Sorted).

b_sort([],Acc,Acc).
b_sort([H|T],Acc,Sorted):-bubble(H,T,NT,Max),b_sort(NT,[Max|Acc],Sorted).
   
bubble(X,[],[],X).
bubble(X,[Y|T],[Y|NT],Max):-X>Y,bubble(X,T,NT,Max).
bubble(X,[Y|T],[X|NT],Max):-X=<Y,bubble(Y,T,NT,Max).

comb([H|_],1,[H]).
comb([_|T],K,C) :- comb(T,K,C).
comb([H|T],K,[H|C]) :- K>1,
                       K1 is K-1,
                       comb(T,K1,C).