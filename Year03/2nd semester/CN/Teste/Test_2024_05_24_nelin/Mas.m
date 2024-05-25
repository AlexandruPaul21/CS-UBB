function [z, ni] = Mas(f, x0, ea, er, nmax)
% MAS - Metoda aproximatiilor succesive pentru ecuatii in R
% apel [z,ni] = mas( f, x0, ea, er, nmax )

if nargin<5, nmax = 100; end
if nargin<4, er = 0; end
if nargin<3, ea = 1e-4; end
for i = 1:nmax
  x1 = f(x0);
  if abs(x0 - x1) < ea || abs(x0 - x1) < er * abs(x1)
      z = x1; ni = i;
      return
  end
  x0 = x1;
end
error('numarul maxim de iteratii depasit')
