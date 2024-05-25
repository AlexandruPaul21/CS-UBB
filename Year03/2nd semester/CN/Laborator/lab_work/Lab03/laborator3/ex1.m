A = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10];
A_pert = [10 7 8.1 7.2; 7.08 5.04 6 5; 8 5.98 9.89 9; 6.99 4.99 9 9.98];
B = [32 23 33 31];
B_pert = [32.1 22.9 33.1 30.9];

x = A \ B';
x_B_pert = A \ B_pert';
x_A_pert = A_pert \ B';

% a) b perturbat
disp("pentru a)")
err_relativa_intrare = norm(B - B_pert) / norm(B)
err_relativa_iesire = norm(x - x_B_pert) / norm(x)
raport = err_relativa_iesire / err_relativa_intrare
% b) A perturbat
disp("pentru b)")
err_relativa_intrare = norm(A - A_pert) / norm(A)
err_relativa_iesire = norm(x - x_A_pert) / norm(x)
raport = err_relativa_iesire / err_relativa_intrare
