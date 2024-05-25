function hkj=HermiteAll(x,r,k,j,t)
%HERMITEALL - compute basic Hermite polynomial h_kj
% Input 
% x - nodes
% r - multiplicities
% k - index of node 
% j - order of differentiation
% t - symbolic var
% Output
% hkj - basic polynomial for x_k and f^(j)
syms u nu z
m=length(x);
i=[1:k,k+2:m];
u=prod((t-x(i)).^(r(i)+1));
z=0;
for nu=0:r(k+1)-j
    z=z+(t-x(k+1))^nu/factorial(nu)*subs(diff(1/u,t,nu),t,x(k+1));
end
hkj=(t-x(k+1))^j/factorial(j)*u*z;
end
