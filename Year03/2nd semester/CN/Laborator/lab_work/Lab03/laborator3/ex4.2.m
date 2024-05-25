% definirea ecuatiei polinomiale
n = 20;
p = 2.^-(1:n); % polinomul x^n+x^(n-1) /2  +x^(n-2) /2^2 + ....


% NEW
function conditionare = custom_cond(poly, root)
    der_poly = polyder(poly);
    p = polyval(poly, root);
    dp = polyval(der_poly, root);
    conditionare = abs(root * dp / p);
end


% calculul radacinilor
roots_exact = roots(p);


% calculul numarului de conditionare al fiecarei radacini
cond_numbers = zeros(n, 1);
% NEW
cond_number_teoretic = zeros(n,1);
p_perturbed = p;
for i = 1:n
    perturbation = normrnd(0,1e-10); % dispersia perturbatiei
    p_perturbed(i) = p_perturbed(i) + perturbation;
    roots_perturbed = roots(p_perturbed);
    cond_numbers(i) = norm(roots_perturbed - roots_exact)/norm(roots_exact);
    % NEW
    cond_number_teoretic(i) = custom_cond(p, i);
end


% graficul numarului de conditionare al fiecarei radacini
figure;
stem(1:n, cond_numbers);
title('Numarul de conditionare al radacinilor');
xlabel('Radacina');
ylabel('Numarul de conditionare');


% NEW
figure;
stem(1:n, cond_number_teoretic);
title('Numarul teoretic de conditionare al radacinilor');
xlabel('Radacina');
ylabel('Numarul de conditionare');
ylabel('Numarul de conditionare');


% perturbare cu variabile aleatoare uniforme
perturbation_uniform = unifrnd(1, n+1) * 1e-10;
p_perturbed_uniform = p + perturbation_uniform;
roots_perturbed_uniform = roots(p_perturbed_uniform);
error_perturbed_uniform = norm(roots_perturbed_uniform-roots_exact)/norm(roots_exact)
