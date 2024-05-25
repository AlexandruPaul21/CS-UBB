function [L, U, P] = lup2(A)
	%LUP find LUP decomposition of matrix A
	%call [L,U,P]=lup(A)
	%logical swapping of lines

	[m, n] = size(A);
	P = zeros(m, n);
  %transposed matrix
	piv = 1: m;

	for i = 1: m - 1
    %pivoting
		%pm-max value, kp-position in column segment
		[pm, kp] = max(abs(A(piv(i: m), i)));
		kp = kp + i - 1;
		l = piv(i);

		%line interchange
		if i != kp
      %swap the entire i and kp rows
			piv([i, kp]) = piv([kp, i]);
		endif

		%Schur complement
		lin = i + 1: m;
		A(piv(lin), i) = A(piv(lin), i) / A(piv(i), i);
		A(piv(lin), lin) = A(piv(lin), lin) - A(piv(lin), i) * A(piv(i), lin);
	endfor

	for i = 1: m
		P(i, piv(i)) = 1;
	endfor

  %upper triangular matrix; sorted rows by piv array
	U = triu(A(piv, :));
  %lower triangular matrix and 1 on the main diagonal
	L = tril(A(piv, :), -1) + eye(m);
endfunction
