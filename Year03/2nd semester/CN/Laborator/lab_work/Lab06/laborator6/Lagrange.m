function fi=Lagrange(x,y,t)
  %LAGRANGE - compute Lagrange interpolation polynomial
  %call fi=Lagrange(x,y,xi)
  % x,y - nodes coordinates
  % t - evaluation points

  if nargin ~=3
      error('Illegal number of arguments')
  end

  [mu,nu]=size(t);
  fi=zeros(mu,nu);
  np1=length(y);
  for i=1:np1
      z=ones(mu,nu);
      for j=[1:i-1,i+1:np1]
          z=z.*(t-x(j))/(x(i)-x(j));
      end
      fi=fi+z*y(i);
  end
endfunction
