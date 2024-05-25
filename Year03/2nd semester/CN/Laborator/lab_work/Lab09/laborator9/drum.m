A=[1 1 1
   1 1 0
   0 1 1
   1 0 0
   0 0 1]
b=[89
   67
   53
   35
   20]

%rezolvare directa
x=A\b
r=b-A*x;  %vectorul rezidual
norm(r); %deviatia totala

%rezolvare cu ecuatii normale
X=A'*A;
y=A'*b;
xn=X\y;
