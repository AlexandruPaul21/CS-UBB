function h=hermite_interpolation_single_nodes(x, y, xi)
% HERMITE_INTERPOLATION - function that computes the Hermite interpolation of the
% given point xi, using values of a function and values of the derivate of the function,
% using Powell's method
% x - nodes
% y - values of the function in the nodes
% xi - the value for which we want to approximate the function
n = length(x);

dd = divdiff(x, y);
h = y(1);
x_prod = 1;
for i=2:n
  x_prod = x_prod * (xi - x(i-1));
  h += x_prod * dd(1, i);
endfor
endfunction
