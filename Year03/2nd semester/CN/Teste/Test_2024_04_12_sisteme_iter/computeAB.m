function [A, B] = computeAB(n)

% compute the diagonals
main_diag = 3 * ones(n, 1);
sub_diag = -1 * ones(n - 1, 1);
sec_diag = 0.5 * ones(n, 1);

% adjust the values
sec_diag(n / 2) = 0;
sec_diag(n - n / 2 + 1) = 0;

% compute the matrix
A = sparse(1:n, 1:n, main_diag, n, n) + sparse(2:n, 1:n-1, sub_diag, n, n) + ...
    sparse(1:n-1, 2:n, sub_diag, n, n) + sparse(1:n, n:-1:1, sec_diag, n, n);

% compute the result matrix
B = [2.5; 1.5*ones((n-4)/2,1); 1.0; 1.0; 1.5*ones((n-4)/2,1); 2.5];

end