function int=MC1(g,a,b,M,n)
X=unifrnd(a,b,1,n);
Y=unifrnd(0,M,1,n);
int=(b-a)*M*mean(Y<=g(X));