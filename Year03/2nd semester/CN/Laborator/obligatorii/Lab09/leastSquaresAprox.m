function [ res ] = leastSquaresAprox( x, y, functions, points )
    % metoda celor mai mici patrate
    % x, y datele de antrenament ; y = f(x)
    % functions - functiile de baza
    % points - punctele de aproxiamt

    phi = functions(x);
    phiApprox = functions(points);

    n = length(x); % numarul de puncte
    [n m] = size(phi); % nr pct * nr functii
    nAprox = length(points); % nr de pct de aproximat

    % A = Z^T * Z ; B = Z^T * y ; unde Z^T e phi
    for i=1:n
        for j=1:n
            A(i,j)=phi(i,:)*transpose(phi(j,:));
        end
        B(i,1)=phi(i,:)*transpose(y);
    end

    %A*a=B
    a=linsolve(A,B);

    res = transpose(a)*phiApprox;
end
