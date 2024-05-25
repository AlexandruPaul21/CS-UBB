function [L, U, P] = lup(A)
	%LUP find LUP decomposition of matrix A
	%call [L,U,P]=lup(A)
	%physical swapping of lines

	[m, n] = size(A);
	P = zeros(m, n);
  %transposed matrix
	piv = (1: m)';

	for i = 1: m-1
    %pivoting
		%pm-max value, kp-position in column segment
		[pm, kp] = max(abs(A(i: m, i)));
		kp = kp + i - 1;

		%line interchange
		if i != kp
      %swap the entire i and kp rows
			A([i, kp], :) = A([kp, i], :);
			piv([i, kp]) = piv([kp, i]);
		endif

		%Schur complement
		lin = i + 1: m;
		A(lin, i) = A(lin, i) / A(i, i);
		A(lin, lin) = A(lin, lin) - A(lin, i) * A(i, lin);
	endfor

	for i = 1: m
		P(i, piv(i)) = 1;
	endfor

  %upper triangular matrix
	U = triu(A);
  %lower triangular matrix and 1 on the main diagonal
	L = tril(A, -1) + eye(m);
endfunction
