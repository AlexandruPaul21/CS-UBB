syms x f;
f = log(1 + x)
R22 = Pade(f, 2, 2, x)
R31 = Pade(f, 3, 1, x)

X = -1:0.01:1.5;
r22 = function_handle(R22)(X);
r31 = function_handle(R31)(X);
func = function_handle(f)(X);
plot(X, func, "--;ln;b", X, r22, "-;R22;r", X, r31, "-;R31;c");
