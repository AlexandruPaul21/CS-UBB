function x = cramer_method(A, B, n)

if n == 2
    x = zeros(2, 1);

    detA  = determinant(2, A);
    detX1 = determinant(2, [B, A(:, 2)]);
    detX2 = determinant(2, [A(:, 1), B]);

    x(1) = detX1 / detA;
    x(2) = detX2 / detA;
end

if n == 3
    x = zeros(3, 1);

    detA  = determinant(3, A);
    detX1 = determinant(3, [B, A(:, 2), A(:, 3)]);
    detX2 = determinant(3, [A(:, 1), B, A(:, 3)]);
    detX3 = determinant(3, [A(:, 1), A(:, 2), B]);

    x(1) = detX1 / detA;
    x(2) = detX2 / detA;
    x(3) = detX3 / detA;
end

end

