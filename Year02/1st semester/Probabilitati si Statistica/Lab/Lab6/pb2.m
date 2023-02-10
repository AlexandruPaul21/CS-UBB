function pb2(g,a,b,M,n=500)
%g1=@(x) exp(-x.^2), a=-2, b=2, M=1
%g2=@(x)  abs(sin(exp(x))), a=-1, b=3, M=1
%g3=@(x)  x.^2./(1+x.^2).*(x<=0)+sqrt(2*x-x.^2).*(x>0), a=-1, b=2, M=1

 %a)
  clf; hold on; 
##  for i=1:n
##    X=unifrnd(a,b);
##    Y=unifrnd(0,M);
##    if g(X)<=Y
##      plot(X,Y,'*r','MarkerSize',10);
##    else
##      plot(X,Y,'*b','MarkerSize',10);
##    endif
##  endfor
  %%% sau, ca sa mearga mai repede:
  
  
  
  X=unifrnd(a,b,1,n); Y=unifrnd(0,M,1,n);
  
  
  plot(X(g(X)<=Y),Y(g(X)<=Y),'*r','MarkerSize',10);
  plot(X(g(X)>Y),Y(g(X)>Y),'*b','MarkerSize',10);
  %%%
  t=linspace(a,b,1000);
  plot(t,g(t),'-k','LineWidth',3);

  int=MC1(g,a,b,M,n)
  int=MC2(g,a,b,n)
  integral(g,a,b)

endfunction
