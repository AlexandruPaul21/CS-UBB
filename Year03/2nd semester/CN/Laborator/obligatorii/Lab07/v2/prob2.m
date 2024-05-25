x = [0.8 1 1.4 1.8 2.1];
f_ = exp(x);

t = 1 : 0.01 : 7;

res = hermite(x, f_, f_, t);
plot(t, res, 'Color', 'red');
hold on;

f = exp(t);
plot(t, f, 'Color', 'green');
hold off;
