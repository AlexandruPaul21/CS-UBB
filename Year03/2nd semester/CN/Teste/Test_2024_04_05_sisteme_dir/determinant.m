function result = determinant(n, A)

if n == 2
    result = A(1,1) * A(2, 2) - A(1, 2) * A(2, 1);
end

if n == 3
    % pentru a economisi flops, vom face totul intr-un rand si vom da
    % factor comun

    result = A(1, 1) * (A(2, 2) * A(3, 3) - A(2, 3) * A(3, 2)) - ...
        A(1, 2) * (A(2, 1) * A(3, 3) - A(2, 3) * A(3, 1)) + ...
        A(1, 3) * (A(2, 1) * A(3, 2) - A(2, 2) * A(3, 1));
end
end