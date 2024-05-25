function H=HermitePoly(x,r,t,f)
%HERMITEPOLY - Hermite Interpolation Polynomial (symbolic)

syms s;
m=numel(x)-1;
s=sym(0);
for k=0:m
    s=s+HermiteAll(x,r,k,0,t)*subs(f,t,x(k+1));
end
for k=0:m
    for j=1:r(k+1)
        s=s+HermiteAll(x,r,k,j,t)*subs(diff(f,t,j),t,x(k+1));
    end
end
H=collect(s,f);
end
