syms f x
f = besselj(0, 2*x);
R22 = Pade(f, 2, 2, x)
R43 = Pade(f, 4, 3, x)
R24 = Pade(f, 2, 4, x)

f = function_handle(f);
R22 = function_handle(R22);
R43 = function_handle(R43);
R24 = function_handle(R24);

i = -2:0.01:2;
plot(i, f(i), "--;Bessel;", i, R22(i), ";R22;", i, R43(i), ";R43;", i, R24(i), ";R24;");
