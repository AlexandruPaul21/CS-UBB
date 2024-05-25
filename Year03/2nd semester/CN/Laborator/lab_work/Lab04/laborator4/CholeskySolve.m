function x=CholeskySolve(A,b)
  %CholeskySolve- solution of an algebraic system by CHOLESKY decomposition
  R = Cholesky(A);

  y = forwardsubst(R', b); %solve R'y=b;
  x = backsubst(R, y);    %solve Rx=y;
endfunction
