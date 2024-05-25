1;
function [A, b] = generate_matrices1(n)
    A = diag(5 * ones(1, n)) + diag(-1 * ones(1, n-1), 1) + diag(-1 * ones(1, n-1), -1);
    b = [4; 3 * ones(n-2, 1); 4];
endfunction



function [A,b] = generate_matrices2(n)
    A = diag(5 * ones(1, n));
    A += diag(-1 * ones(1, n-1), 1) + diag(-1 * ones(1, n-1), -1);
    A += diag(-1 * ones(1, n-3), 3) + diag(-1 * ones(1, n-3), -3);
    b = [3; 2; 2; 1 * ones(n-6, 1); 2;2;3];
endfunction




n=input('Enter n:')

%[A,b]=generate_matrices1(n);
[A,b]=generate_matrices2(n);
disp('Matrix A:');
%disp(A);
disp('Array b:');
%disp(b);
fprintf("\n\n\n")


disp("Jacobi results")
[x, nrIt] = Jacobi(A, b)
fprintf("\n\n\n")


disp("GaussSeidel results")
[x, nrIt] = GaussSeidel(A, b)
fprintf("\n\n\n")


disp("SOR results")
omega=OptimiumRelaxPar(A)
[x, nrIt] = SOR(A, b, omega)
