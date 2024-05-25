function solution = cholesky_solve(A, B)

b1 = A' * B;
R = cholesky(A' * A);
[~, n] = size(R);
y = zeros(n, 1);
for i = 1:n
    y(i) = (b1(i) - R(1:i-1, i)' * y(1:i-1)) / R(i, i);
end
solution = zeros(n, 1);
solution(n) = y(n) / R(n, n);
for i = n-1:-1:1
    solution(i) = (y(i) - R(i, i+1:n) * solution(i+1:n)) / R(i, i);
end

end