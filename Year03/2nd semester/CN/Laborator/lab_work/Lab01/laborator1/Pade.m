syms x;
function R = Pade(f, m, k, x)
  %PADE - compute Pade approximant
  %call R = Pade(f, m, k, x)
  %f - symbolic function to approximate
  %m - numerator(numarator) polinom order
  %k - denominator(numitor) polinom order
  %x - function's indeterminant


  %row vector, pentru polinomul de la numarator
  a = sym(zeros(1, m + 1));
  %column vector
  % pentru polinomul de la numitor
  b = sym(zeros(max(m, k) + 1, 1));
  c = sym(zeros(m + k + 1, 1));


  % compute Taylor coeficients
  for p = 0 : m + k
    c(p + 1) = subs(diff(f, x, p), x, 0) / factorial(p);
  endfor


  if k == 0
    % c' transpusa, .* inmultire termeni cu coeficienti, .^ puterile lui x
    R = sum(c' .* x .^ (0:m));
    return;
  endif

  % build Toeplitz matrix
  % first column generation
  col = c(m + 1 : m + k);
  row = sym(zeros(k, 1));
  % make sure no negative indices are used
  endIndex = max(1, m - k + 2);
  row(1 : m + 2 - endIndex) = c(m + 1 : -1 : endIndex);
  C = toeplitz(col, row);

  % find b
  bv = -c(m + 2 : m + k + 1);
  b(2 : k + 1) = C \ bv;
  b(1) = 1;


  % find a
  for j = 1 : m + 1
    a(j) = sym(0);
    for l = 0 : j - 1
      a(j) = a(j) + c(j - l) * b(l + 1);
    endfor
  endfor

  vp = sum(a .* x .^ (0 : m));
  vq = sum(b(1: k + 1)' .* x .^ (0 : k));
  R = vp / vq;
endfunction

fh = function_handle(exp(x));
Pade(fh,3,3,x)
