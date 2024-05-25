n = input("Enter n:")
b = [];
for i = 2: -1 : -n+2
    b = [b; i];
end

A = zeros(n+1, n+1);

for i = 1 : n+1
    for j = 1 : n+1
        if i == j
            A(i, j) = 1;
            continue
        end

        if j == n + 1
            A(i, j) = 1;
            continue
        end

        if j < i
            A(i, j) = -1;
        end
    end
end

function x=QrSolve(A, b)
  %qq'qq=I, rr=matrice triunghiulara superioara
  [qq, rr] = qr(A);
  x = rr \ qq.' * b;
end

A
det(A)
solLUP = LupSolve(A, b)
solQR =  QrSolve(A, b)

A * solLUP
A * solQR


