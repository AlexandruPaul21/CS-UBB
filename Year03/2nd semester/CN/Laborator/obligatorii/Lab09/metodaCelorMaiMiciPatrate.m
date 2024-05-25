function [res] = metodaCelorMaiMiciPtrate(x, y, F, puncteAprox)
  % y = f(x)
  % F - functii de baza
  % puncteAprox - puncte de aproximat

  phi = F(x);
  phiAprox = F(puncteAprox);

  n = length(x); % nr de puncte
  [n,m] = size(phi); % nr pct * nr de fct
  nAprox = length(puncteAprox); % nr de pct de aproximat

  % A = Z^T * Z;
  % B = Z^T * y;
  % Z^T = phi
  for i = 1:n
    for j = 1:n
      A(i,j) = phi(i,:) * transpose(phi(j,:));
    endfor
    B(i,1) = phi(i,:) * transpose(y);
  endfor

  a = linsolve(A, B);
  res = transpose(a) * phiAprox;
endfunction
