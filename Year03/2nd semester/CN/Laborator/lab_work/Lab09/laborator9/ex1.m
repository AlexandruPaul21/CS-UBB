a = 0;
b = 2*pi;

% generarea de puncte pe intervalul [a, b]
n_points = 50;
x = linspace(a, b, n_points);


y = cos(x);
n = 3;
coefficients = polyfit(x, y, n);

% evalaurea polinomului
approximated_poly = polyval(coefficients, x)

% plot rezultate
figure;
plot(x, y, 'b', x, approximated_poly, 'r');
legend('f(x)', 'Aproximare polinomială');
xlabel('x');
ylabel('y');
title('Aproximare polinomială a unei funcții continue');
