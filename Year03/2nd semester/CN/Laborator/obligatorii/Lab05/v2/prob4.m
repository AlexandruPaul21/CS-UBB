n = 9;

A = rand(n);
A = A + eye(n) * n;
sol = [1: n]';
b = A * sol;
disp(A);
disp(b);
err = 10^(-6);

Jacobi = rezolvareJacobi(A, b, err)
Gauss_Seidel = rezolvareGaussSeidel(A, b, err)

M = diag(diag(A));
N = M-A;
T = inv(M)*N;
e = eig(T); % vector de valori proprii
p = max(abs(e)); %raza spectrala a matricei Jacobi
omega = 2/(1+sqrt(1-p^2));

M = diag(diag(A))
N = M-A;
T = inv(M)*N;
e = eig(T); % vector de valori proprii
p = max(abs(e)); %raza spectrala a matricei Jacobi
omega = 2/(1+sqrt(1-p^2));

SOR = rezolvareSOR(A, b, omega, err)
