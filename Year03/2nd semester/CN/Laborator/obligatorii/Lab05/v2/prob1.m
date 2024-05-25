% Metoda Jacobi
A = [6, -4, 1; 2, 6, 0; 2, -5, 8];  % A - matricea sistemului
b = [1; 2; -5];   % b - vectorul termenilor liberi
err = 10^(-6);    % err - eroarea de calculare a solutiei

M = diag(diag(A))
N = M - A
invM = inv(M); % inversa matricii M
T = invM * N;  % matricea de iteratie
c = invM * b;  % vectorul de iteratie c
alfa = norm(T, inf);  % norma infinit a matricei T - pentru a sti daca iteratie din bucla continua sau nu

x0 = zeros(size(b)); % estinez ca solutie este 0 0 0 0
x=x0(:);             % il pun pe coloana

ok = 0;
while ok == 0
   x0 = x;
   x = T*x0+c;
   if norm(x-x0, inf) < (1-alfa) / alfa*err  %criteriul de oprire
     ok = 1;
   endif
endwhile

disp(x);

