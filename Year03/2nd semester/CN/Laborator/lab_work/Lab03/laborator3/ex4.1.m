% definirea ecuatiei polinomiale
n = 20;
p = poly(1:n); % polinomul (x - 1)(x - 2)...(x - n)


% NEW
function conditionare = custom_cond(poly, root)
    %derivata polinomului
    der_poly = polyder(poly);
    %evaluarea polinomului in punctul root
    p = polyval(poly, root);
    %evaluarea derivatei in punctul root
    dp = polyval(der_poly, root);
    % calculul conditionarii
    conditionare = abs(root * dp / p);
end


% calculul radacinilor
roots_exact = roots(p);


% calculul numarului de conditionare al fiecarei radacini
cond_numbers = zeros(n, 1);
% NEW
cond_number_teoretic = zeros(n,1)
for i = 1:n
    perturbation = normrnd(0,1e-10); % dispersia perturbatiei
    p_perturbed = p;

    %de fiecare data perturb un coeficient
    p_perturbed(i) = p_perturbed(i) + perturbation;

    %aflam iar radacinile si conditionarea (aka cat de mult a influentat
    %perturbarea noastra rez final)
    roots_perturbed = roots(p_perturbed);
    cond_numbers(i) = norm(roots_perturbed - roots_exact)/norm(roots_exact);
    % NEW
    cond_number_teoretic(i) = custom_cond(p, i)
end


% graficul numarului de conditionare al fiecarei radacini
figure;
stem(1:n, cond_numbers);
title('Numarul de conditionare al radacinilor');
xlabel('Radacina');
ylabel('Numarul de conditionare');
%teoretic pt ca prima linie e mai lunga inseamna ca perturbarea primului
%coef influenteaza cel mai tare rezultatul


% NEW
figure;
stem(1:n, cond_number_teoretic);
title('Numarul teoretic de conditionare al radacinilor');
xlabel('Radacina');
ylabel('Numarul de conditionare');


% perturbare cu variabile aleatoare uniforme
perturbation_uniform = unifrnd(1, n+1) * 1e-10;
p_perturbed_uniform = p + perturbation_uniform;
roots_perturbed_uniform = roots(p_perturbed_uniform);
error_perturbed_uniform = norm(roots_perturbed_uniform-roots_exact)/norm(roots_exact)

