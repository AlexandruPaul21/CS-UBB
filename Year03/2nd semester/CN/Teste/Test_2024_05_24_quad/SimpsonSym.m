function I = SimpsonSym(f, a, b, n)
% Simson simbolic pentru analize teoretice
    if mod(n,2) ~= 0
        n = n + 1;
    end

    m = n / 2;
    h = (b - a) / n;

    sum1 = 0; % 2 * f(x_2k)
    sum2 = 0; % 4 * f(x_(2k-1))

    for k = 1:m-1
        x2k = a + 2*k*h;
        sum1 = sum1 + f(x2k);
    end

    for k = 1:m
        x2k_1 = a + (2*k-1)*h;
        sum2 = sum2 + f(x2k_1);
    end

    I = h/3 * (f(a) + f(b) + 2 * sum1 + 4 * sum2);
end
