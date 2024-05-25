%Sisteme Liniare. Metode directe
%Pentru generarea unui sistem A*x=B 
%cu o matrice A aleatoare si 
%solutia x=[1 1 1 ... 1]' putem folosi comenzile:
n=5;
A=rand(n);
B=sum(A,2);
x=A\B
pause
fprintf('LUP \n')
%LUP
% PA=LU
%(PA)x=PB
%L(Ux)=PB
%Ly=PB =>y=substitutie directa
%Ux=y =>x=substitutie inversa
[L, U,P]=lu(A)
y=forwardsubst(L,P*B)
x=backsubst(U,y)
pause

fprintf('Choleski \n')
%Pentru generarea unei matrici hermitiene
%si pozitiv definite putem folosi comenzile:
n=5;
A=rand(n);
A=A+A';
A=A+max(sum(A,2))*eye(n)
B=sum(A,2);
%Cholesky
%(R'R=A)
%Ax=B
%R'(Rx)=B
%R'y=B => y=substitutie directa
%Rx=y =>x=substitutie inversa
R=chol(A)
y=forwardsubst(R',B)
x=backsubst(R,y)
pause
fprintf('QR \n')
%QR
%QR=A
%QRx=B
%Rx=Q'B =>x=substitutie inversa
n=5;
A=rand(n);
B=sum(A,2);
[Q,R]=qr(A)
x=backsubst(R,Q'*B)


