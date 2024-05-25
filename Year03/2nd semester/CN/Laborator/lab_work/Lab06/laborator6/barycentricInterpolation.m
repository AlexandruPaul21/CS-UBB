function ff=barycentricInterpolation(x,y,xx,c)
  %BARYCENTRICINTERPOLATION - barycentric Lagrange interpolation
  %call ff=barycentricInterpolation(x,y,xx,c)
  %x -  nodes
  %y - function values
  %xx - interpolation points
  %c - barycentric weights
  %ff - values of interpolation polynomial

  n=length(x)-1; %numarul de intervale dintre noduri
  numer = zeros(size(xx));  %numarator
  denom = zeros(size(xx));  %numitor
  exact = zeros(size(xx));
  for j=1:n+1
      %nod_interpolare - nod_curent
      xdiff = xx-x(j);
      temp = c(j)./xdiff;
      %ajustare numarator si numitor
      numer = numer+temp*y(j);
      denom = denom+temp;
      %marcheaza indicii unde xx=x(j)
      exact(xdiff==0) = j;
  end
  %valorile interpolare finale
  ff = numer ./ denom;
  %gaseste indicii valorilor din xx egale cu valorile din x
  jj = find(exact);
  ff(jj) = y(exact(jj));
endfunction
