function L=lagrange(x,y,xi)
  % calculeaza polinomul de interpolare Lagrange
  % x,y - coordonatele nodurilor
  % xi - punctele in care se evalueaza polinomul

  [mu, nu] = size(xi);
  L = zeros(mu, nu);
  np1 = length(y);
  for i = 1:np1
      z = ones(mu, nu);
      for j = [1:i-1,i+1:np1]
          z=z.*(xi-x(j))/(x(i)-x(j));
      endfor
      L=L+z*y(i);
  endfor

endfunction

