x = [0 1 2];
fx = exp(x);
dfx = exp(x);

f_lagrange = lagrange(x, fx, 0.25)
f_hermite = hermite(x, fx, dfx, 0.25)
exponent = exp(0.25)
