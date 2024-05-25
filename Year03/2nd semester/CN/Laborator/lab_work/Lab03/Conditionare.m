 %Wilson
 format long
 W=[10 7 8 7;
     7 5 6 5;
     8 6 10 9;
     7 5 9 10]
 det(W)
 cond(W)
 norm(W)*norm(inv(W))
 pause


%Rutishauser
R=[10     1     4     0;
   1    10     5    -1;
     4     5    10     7;
     0    -1     7     9]
 det(R)
 cond(R)
 
 pause
 format rat
 H5=hilb(5)
 
 pause
 t=-1:0.25:1;
 V=vander(t)
 
 %pag 50 culegere, conditionarea radacinii unui polinom
 p=[1 -3 2];% p(x)=x^2-3*x+2
 x=roots(p)% x=[2 1]; x(1)=2; x(2)=1
 P20=poly(1:20) %coeficientii polinomului cu radacinile 1, 2, 3, ...,20
 roots(P20) %radacinile polinomului P20
 
 