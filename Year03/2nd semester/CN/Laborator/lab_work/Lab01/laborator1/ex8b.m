syms x;
R11 = Pade(exp(x), 1, 1, x)
R22 = Pade(exp(x), 2, 2, x)
R30 = Pade(exp(x), 3, 0, x)

X = -1.5:0.01:1.5;
r11 = function_handle(R11)(X);
r22 = function_handle(R22)(X);
r30 = function_handle(R30)(X);
exponential = exp(X);
plot(X, exponential, "--;exp;b", X, r11, "-;R11;r", X, r22, "-;R22;c", X, r30, "-;R30;m");
