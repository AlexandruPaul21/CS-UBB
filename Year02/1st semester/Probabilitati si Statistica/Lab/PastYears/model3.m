% Generam 500 de valori aleatoare pentru X
X = zeros(500, 1);
for i = 1:500
  % Generam un numar aleatoriu uniform distribuit intre 0 si 1
  r = rand();
  
  % Folosim acest numar pentru a determina care distribuţie trebuie să folosim pentru X
  if r < 0.1
    X(i) = -2;
  elseif r < 0.5
    X(i) = -1;
  elseif r < 0.8
    X(i) = 1;
  else
    X(i) = 2;
  endif
end

% Generam 500 de valori aleatoare pentru Y
Y = -1 + 5 .* rand(500, 1);

% Calculam valorile lui U
U = X.^3 - Y.^3;

disp("a)")
% Generam histograma frecvenţelor absolute pentru U, având 20 de clase
hist(U, 20)

disp("b)")
% Estimam P(U < 0)
p_u_lt_0 = sum(U < 0) / length(U)

% Estimam valoarea medie a lui U
mean_u = mean(U)

% Estimam varianţa lui U
var_u = var(U)

disp("c)")
% Calculam valoarea medie teoretică a lui X^3
mean_x3 = -2 * 0.1 + -1 * 0.4 + 1 * 0.3 + 2 * 0.2
