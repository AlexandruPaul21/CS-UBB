n = 1e1;

eps = 1e-3;
nritmax = 1e2;
x0 = zeros(n, 1);

A = randi(n, n); % matrice nxn cu elemente 1:n
for i = 1:n
  A(i, i) = A(i, i) + sum(A(i,:)) + randi(n); % devine matrice diagonal dominanta
end

x_real = (1:n)';
b = A * x_real;

% Jacobi
disp("Metoda lui Jacobi:");
[x, nrit] = Jacobi(A, b, x0, eps, nritmax);
disp("Eroare:");
norm(x - x_real)
nrit

% Gauss-Siedel
disp("Metoda Gauss-Siedel:");
[x, nrit] = GaussSeidel(A, b, x0, eps, nritmax);
disp("Eroare:");
norm(x - x_real)
nrit

% SOR
disp("Metoda SOR:");
[x, nrit] = SOR(A, b, 1.071, x0, eps, nritmax);
disp("Eroare:");
norm(x - x_real)
nrit
