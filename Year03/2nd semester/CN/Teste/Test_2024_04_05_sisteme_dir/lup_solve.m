function solution = lup_solve(A, B)

[L, U, P] = lup(A);

% permute the elements of the constant vector b in the correct order
B_permuted = P * B;

% solve the system Ly = b_permuted using forward substitution
n = size(A, 1);
y = zeros(n, 1);
for i = 1:n
    y(i) = (B_permuted(i) - L(i, 1:i-1) * y(1:i-1)) / L(i, i);
end

% solve the system Ux = y using back substitution
x = zeros(n, 1);
for i = n:-1:1
    x(i) = (y(i) - U(i, i+1:n) * x(i+1:n))/U(i, i);
end

solution = x;

end