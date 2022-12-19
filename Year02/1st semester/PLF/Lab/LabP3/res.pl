% check(S: lista, NrD: intreg, NrI: intreg) 
% Modele de flux (i,i,i) - determinist
% S - lista de paranteze inchise sau deschise, care se verifica
% NrD - Nr de paranteze deschise
% NrI - Nr de paranteze inchise
check([], Nr, Nr).
check([H | T], NrD, NrI) :- NrD >= NrI, H = '(', NrD1 is NrD + 1, 
                            check(T, NrD1, NrI).
check([H | T], NrD, NrI) :- NrD >= NrI, H = ')', NrI1 is NrI + 1, 
                            check(T, NrD, NrI1).

% par(N: intreg, Res: lista, ResF: lista)
% Modele de flux (i,i,o) - nedeterminist, (i,i,i) - determinist
% N - nr de paranteze pe care trebuie sa le aiba rezultatul
% Res - lista de rezultat intermediara
% ResF - lista cu rezultatele ce se obtin la anumiti pasi in rezolvare
par(0, Res, ResF) :- !, check(Res, 0, 0), ResF = Res.
par(N, Res, ResF) :- N1 is N - 1, par(N1, ['(' | Res], ResF).
par(N, Res, ResF) :- N1 is N - 1, par(N1, [')' | Res], ResF).

% par_main/_2(N: intreg, Res: lista)
% Modele de flux (i, o) - nedeterminist, (i, i) - determinist
% N - nr de paranteze pe care trebuie sa le aiba rezultatul
% Res - lista rezultat 
par_main(N, ResF) :- par(N, [], ResF).

par_main_2(N, ResF) :-  findall(Rez, par(N, [], Rez), ResF).