A = [6, -4, 1; 2, 6, 0; 2, -5, 8];
b = [1; 2; -5];
err = 10^(-6);

% determin valoarea optima a parametrului omega
M = diag(diag(A))
N = M - A;
T = inv(M) * N;
e = eig(T); % vector de valori proprii
p = max(abs(e)); %raza spectrala a matricei Jacobi
omega = 2/(1+sqrt(1-p^2));


% metoda relaxarii (Successive OverRelaxation)
[n, ~]=size(A);
x0=zeros(size(b));

M = 1 \ omega*diag(diag(A))+tril(A,-1);
N = M-A;
T = M\N;
c = M\b;
alfa = norm(T, inf);
x = x0(:);
ok = 1;
while ok == 1
    x0 = x;
    x = T*x0 + c;
    if norm(x-x0, inf) < (1-alfa) / alfa*err
        ok = 0;
    endif
endwhile

disp(x);
