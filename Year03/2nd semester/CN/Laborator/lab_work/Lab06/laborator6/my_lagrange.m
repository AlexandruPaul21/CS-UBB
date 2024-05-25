function interpolation_result = my_lagrange(x, y, xi)
  % x-array-ul de noduri
  % y-array-ul cu valorile polinomului in punctele din x
  % verificarea nr de arugmente
  if nargin != 3
    error('Bad number of arguments')
  endif

  m = length(y); % numarul de noduri
  n = length(xi); % nr punctelor de interpolare
  interpolation_result = zeros(1, n); % initializarea rezultatului

  % loop prin fiecare punct de interpolare
  for k = 1:n
    xi_k = xi(k);
    sum_l = 0;

    for i = 1:m
      l = 1;
      for j = [1:i-1, i+1:m]
        l = l * (xi_k - x(j)) / (x(i) - x(j));
      endfor
      sum_l += l * y(i);
    endfor

    interpolation_result(k) = sum_l;
  endfor

endfunction
