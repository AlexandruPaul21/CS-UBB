function td=divdiff(x,f)
%DIVDIFF - compute divided difference table
%call td=divdiff(x,f)
%x - nodes
%f- function value
%td - divided difference table

lx=length(x);
f=f(:); x=x(:);
td=zeros(lx,lx);
td(:,1)=f;
x
for j=2:lx
  td
  td(1:lx-j+1,j)=diff(td(1:lx-j+2,j-1))./(x(j:lx)-x(1:lx-j+1));
end
