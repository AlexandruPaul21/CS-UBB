function I=trapezes(f,a,b,n)
%TRAPEZES trapezes formula
%call I=trapezes(f,a,b,n);

h=(b-a)/n;
I=(f(a)+f(b)+2*sum(f((1:n-1)*h+a)))*h/2;