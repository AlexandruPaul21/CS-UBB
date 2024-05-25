function conditionare = conditionare_polinomiala(p,r)
% conditionarea unei ecuatii polinomiale p(x)=0

    if nargin < 2  %verific daca am destule argumente date functiei
        r = roots(p);  %daca nu au fost date calculez polinomul
    end

    % calculam derivata p'(x)
    n = length(p)-1;

    %produsul dintre gradul polinomului si vectorul coef cu exceptia ultimului coef
    derivata = [n:-1:1] .* p(1:end-1);

    %evaluez val derivatei pol
    val_df = polyval(derivata,r); % valoarea derivatei in fiecare radacina

    %evaluez pol derivat
    poliv = polyval(abs(p(2:end)),abs(r));

    %calculez factorul de conditionare
    conditionare = poliv./(abs(r.*val_df));
end
