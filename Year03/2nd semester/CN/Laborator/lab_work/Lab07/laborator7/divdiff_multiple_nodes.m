function td=divdiff_multiple_nodes(x, f, f_der)
  lx = length(x);
  f=f(:); x=x(:); % transform line vectors into column vectors
  td=zeros(lx,lx);
  td(:, 1) = f;
  for i=1:lx-1
    if f(i+1) - f(i) == 0
      td(i, 2) = f_der(i);
    else
      td(i, 2) = (f(i+1) - f(i)) / (x(i+1) - x(i));
    endif
  endfor
  for j=3:lx
    td(1:lx-j+1,j)=diff(td(1:lx-j+2,j-1))./(x(j:lx)-x(1:lx-j+1));
  endfor
endfunction
