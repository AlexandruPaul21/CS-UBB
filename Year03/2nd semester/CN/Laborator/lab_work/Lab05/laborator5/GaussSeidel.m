function [x, nrIt] = GaussSeidel(A, b, x0 = zeros(size(b)), err = 1e-6, nrItMax = 1000)
  % GAUSSSEIDEL - metoda GaussSeidel de rezolvare a sistemelor
  % call [x, nrIt] = GaussSeidel(A, b)
  % call [x, nrIt] = GaussSeidel(A, b, x0)
  % call [x, nrIt] = GaussSeidel(A, b, x0, err)
  % call [x, nrIt] = GaussSeidel(A, b, x0, err, nrItMax)
  % A - matricea sistemului
  % b - vectorul termenilor liberi
  % x0 - punctul de plecare, implicit zeros(size(b), 1)
  % err - toleranta, implicit 1e-3
  % nrItMax - numarul maxim de iteratii, implicit 1000
  % x - solutia
  % nrIt - numarul de iteratii efectuate

  %matricea triunghiulara inferioara
  M = tril(A);
  N = M - A;

  T = M \ N;  %MT=N;
  c = M \ b;  %Mc=b;

  %norma la inf a matricei T
  normT = norm(T, inf);
  %calculul criteriului de oprire
  stopCrit = (1 - normT) / normT * err;

  for i = 1 : nrItMax
    x = T * x0 + c;
    if norm(x - x0, inf) <= stopCrit
      nrIt = i;
      return;
    endif
    x0 = x;
  endfor
  warning("Depasit numarul de iteratii");
  nrIt = nrItMax;
endfunction
