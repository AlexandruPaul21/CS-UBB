function R = RombergSym(f, a, b, n)
% Romberg simbolic pentru analize teoretice

R = sym(zeros(n, n));
h = b - a;
R(1, 1) = h / 2 * (sum(f([a, b])));
for k=2:n
    % trapezes formula
    x = a + (((1:2^(k - 2)) - sym(1) / sym(2)) * h);
    R(k, 1) = sym(1) / sym(2) * (R(k - 1, 1) + h * sum(f(x)));
    % extrapolation
    plj = sym(4);
    for j = 2:k
        R(k, j) = (plj * R(k, j - 1) - R(k - 1, j - 1)) / (plj - 1);
        plj = plj * sym(4);
    end
    h = h / sym(2);
end
end
