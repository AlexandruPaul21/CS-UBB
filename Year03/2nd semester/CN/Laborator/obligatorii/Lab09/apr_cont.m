% Problema 2 b)
f = @(x) x.^2 .* sin(x);

a = -2 * pi;
b = 2 * pi;
N = 11;

% metoda celor mai mici patrate
x_patrate = linspace(a, b, N);

% valoarea fct in noduri
f_patrate = f(x_patrate);
phi = @(x)[ones(1, length(x)); x; x.^2; sin(x); x.*sin(x); x.^2.*sin(x)];

puncte = linspace(a, b, 20);
rez_patrate = metodaCelorMaiMiciPatrate(x_patrate, f_patrate, phi, puncte)

X = linspace(a, b, 100);
Y = f(X);
plot(X, Y, 'Color', 'blue', 'LineWidth', 1);
hold on;
plot(puncte, rez_patrate, 'Color', 'red', 'LineWidth', 1.5);
hold off;

