syms x;
f = exp(x); % Define the function
m = 2; % Order of the numerator
k = 2; % Order of the denominator

% Call the pade_sym function
R = pade_sym(f, m, k, x);

% Display the result
disp('Pade Approximant:');
disp(R);
