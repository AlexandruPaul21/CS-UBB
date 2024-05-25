function [ res ] = adapt( f, a, b, eps, met )
% adaptive integration technique to achieve the desired accuracy eps
m = 5;
if abs(met(f, a, b, m) - met(f, a, b, 2 * m)) < eps
   res = met(f, a, b, 2 * m);
else
    res = adapt(f, a, (a + b) / 2, eps, met) + adapt(f, (a + b) / 2, b, eps, met);
end
end
