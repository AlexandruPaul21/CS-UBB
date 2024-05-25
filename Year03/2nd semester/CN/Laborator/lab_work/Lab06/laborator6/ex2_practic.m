x = [1, 1.1, 1.2, 1.3, 1.4];
function f = myF(x)
    f = exp(x^2-1);
end

y = arrayfun(@myF, x)


t=1.25
lagrange_aprox = Lagrange(x,y,t)
actual_values = myF(t)
