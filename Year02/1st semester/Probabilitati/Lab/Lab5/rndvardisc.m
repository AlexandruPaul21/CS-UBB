function X=rndvardisc(x,p,N)
  q=cumsum(p);
  X=zeros(1,N);
  for i=1:N
    U=rand;
    j=1;
    while U>q(j)
      j++;
    endwhile
    X(i)=x(j);
  endfor
endfunction
