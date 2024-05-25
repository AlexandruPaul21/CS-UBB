1;
n = input('n = ');
A = rand(n)
b = A * ones(n, 1);



disp('Rezolvare cu Gausselim')
tic
x = Gausselim(A, b)
toc
fprintf('\n\n\n');




disp('Rezolvare cu LUP')
tic
x = LupSolve(A,b)
toc
fprintf('\n\n\n');


