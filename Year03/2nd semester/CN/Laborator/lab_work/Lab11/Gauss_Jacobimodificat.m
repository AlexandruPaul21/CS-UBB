function [g_nodes,g_coeff]=Gauss_Jacobimodificat(n,a,b)
%Gauss-Jacobimodificat - Gauss-Jacobi nodes and coefficients
%weight function w(t)=(1-t)^a(1+t)^b

k1=1:n-1;
k2=2:n-1;
%rec. relation coeffs
bet0=2^(a+b+1)*gamma(a+1)*gamma(b+1)/gamma(a+b+2);
bet1=4*(1+a)*(1+b)/((2+a+b)^2)/(3+a+b);
bet=[bet0, bet1, 4*k2.*(k2+a+b).*(k2+a).*...
        (k2+b)./(2*k2+a+b-1)./(2*k2+a+b).^2./(2*k2+a+b+1)];
if a==b
    alpha=zeros(1,n);
else
    %alpha1=(b^2-a^2)/(a+b);
    
    alpha=[(b-a)./(a+b+2),(b^2-a^2)./(2*k1+a+b)./(2*k1+a+b+2)];
end
%alpha=[alpha1,(b^2-a^2)./(2*k+a+b)./(2*k+a+b+2)];
[g_nodes,g_coeff]=Gaussquad(alpha,bet);