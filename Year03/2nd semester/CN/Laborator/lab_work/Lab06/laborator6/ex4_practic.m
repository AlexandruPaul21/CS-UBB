1;

%a)
%{
function f = myF(x)
    f = 1/(1+x^2);
end

n = 21; a = -5; b = 5; k = 1:n;
% noduri Cebisev
x = 0.5 * (b - a) * cos(((0:n) * pi) / n) + 0.5 * (a + b);
% noduri echidistante
x = linspace(a,b,n);


y = arrayfun(@myF, x)
t = linspace(a, b, 100)
lagrange_aprox = Lagrange(x,y,t)
actual_values = arrayfun(@myF, t)

plot(t, lagrange_aprox, 'r');
hold on;
plot(t, actual_values, 'b');
hold off;
legend('Lagrange Approximation', 'Real Values', 'Location', 'northeast');
title('Comparison of Lagrange Approximation with the Exact Values');
xlabel('t');
ylabel('Function Value');
%}






%b)
function f = myF(x)
    f = abs(x);
end

n = 11; a = -1; b = 1; k = 1:n;
% noduri Cebisev
% x = 0.5 * (b - a) * cos(((0:n) * pi) / n) + 0.5 * (a + b);
% noduri echidistante
x = linspace(a,b,n);


y = arrayfun(@myF, x);
t = linspace(a, b, 100)
lagrange_aprox = Lagrange(x,y,t)
actual_values = arrayfun(@myF, t)

plot(t, lagrange_aprox, 'r');
hold on;
plot(t, actual_values, 'b');
hold off;
legend('Lagrange Approximation', 'Real Values', 'Location', 'northeast');
title('Comparison of Lagrange Approximation with the Exact Values');
xlabel('t');
ylabel('Function Value');

