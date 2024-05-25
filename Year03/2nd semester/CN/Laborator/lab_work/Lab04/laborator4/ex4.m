1;
n = input('n = ');
function A = generate_hpd_matrix(n)
    G = rand(n);
    A = G' * G;
    A = A + n * eye(n);
endfunction
A = generate_hpd_matrix(n);
%b = randi([1,5],n,1)
b = A * ones(n, 1);



disp('Rezolvare cu Cholesky')
tic
x = CholeskySolve(A,b)
toc
fprintf('\n\n\n');
