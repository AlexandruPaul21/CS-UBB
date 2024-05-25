function [x, nrIt] = SOR(A, b, omega, x0 = zeros(size(b)), err = 1e-6, nrItMax=1000)
  % SOR - metoda SOR (successive overrelaxation) de rezolvare a sistemelor
  % call [x, nrIt] = SOR(A, b, omega)
  % call [x, nrIt] = SOR(A, b, omega, x0)
  % call [x, nrIt] = SOR(A, b, omega, x0, err)
  % call [x, nrIt] = SOR(A, b, omega, x0, err, nrItMax)
  % A - matricea sistemului
  % b - vectorul termenilor liberi
  % omega - parametrul de relaxare
  % x0 - punctul de plecare, implicit zeros(size(b), 1)
  % err - toleranta, implicit 1e-3
  % nrItMax - numarul maxim de iteratii, implicit 1000
  % x - solutia
  % nrIt - numarul de iteratii efectuate

  M = 1 / omega * diag(diag(A)) + tril(A, -1);
  N = M - A;

  T = M \ N;  %MT=N;
  c = M \ b;  %Mc=b;

  normT = norm(T, inf);
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
