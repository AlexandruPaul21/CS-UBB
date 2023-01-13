function int=MC2(g,a,b,n)
int=(b-a)*mean(g(unifrnd(a,b,1,n)));