function ff = Hermite(x, y, yd, xx)
% Hermite - calculates the Hermite interpolation
% x - nodes
% y - function
% yd - function derivative
% xx - evaluation points

% calculam tabelul de diferente divizate
[xd, T] = div_diff_double(x, y, yd);

% interpolăm folosind formula lui Newton
lt = length(xx); lx = length(xd);
for j = 1:lt
   d = xx(j) - xd;
   ff(j)=[1, cumprod(d(1:lx-1))] * T(1,:)';

end