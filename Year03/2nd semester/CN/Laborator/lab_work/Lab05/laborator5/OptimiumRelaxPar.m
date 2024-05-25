function omega =  OptimiumRelaxPar(A)
  % OPTIMUMRELAXPAR - gaseste parametrul optim de relaxare pentru metoda SOR
  % call omega = OptimiumRelaxPar(A)
  % A - matricea pentru care se cauta parametrul
  % omega - parametrul optim de relaxare

  % calculeaza matricea Jacobi
  M = diag(diag(A));
  N = M - A;
  T = M \ N;

  % calculeaza raza spectrala a matricei Jacobi
  e = eig(T);
  rt = max(abs(e));
  omega = 2 / (1 + sqrt(1 - rt ^ 2));
endfunction
