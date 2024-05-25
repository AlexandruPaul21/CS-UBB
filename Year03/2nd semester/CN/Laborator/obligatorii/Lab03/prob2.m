function prob2
  warning('off')
  disp('a');
  for n = 10:15   %parcurg nr de la 10 la 15
    k = linspace(-1,1,n);  %k contine n elem echidistante in int [-1, 1]
    t = -1 + k .* (2/n);   % se calc punctele folosind formula data
    V = vander(t);         % se constrieste mat Vandermonde
    result = cond(V, inf); % se cal conditionarea matricei V folosind norma infinit
    disp(num2str(result));
  endfor

  disp('');
  disp('b');
  for n = 10:15
    k = 1:n;
    t = ones(1, n)./k;
    V = vander(t);
    result = cond(V, inf);
    disp(num2str(result));
  endfor
endfunction
