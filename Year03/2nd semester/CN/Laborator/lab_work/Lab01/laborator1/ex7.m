format long;
T = @(n)taylor(log(1+x), 'order', n+1);

fh = function_handle(log(1+x));
subplot(3, 2, 1);
fplot(fh, [-3, 3]);

for i = 2:5
  fhT = function_handle(T(i));
  subplot(3, 2, i);
  fplot(fhT, [-3, 3]);
endfor

