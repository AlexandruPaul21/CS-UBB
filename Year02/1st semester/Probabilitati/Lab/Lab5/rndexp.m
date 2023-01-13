function X=rndexp(N,lambda)
  X=-log(1-rand(1,N))/lambda;
endfunction
