function R = Cholesky(A)
	%CHOLESKY - Cholesky factorization
	%call R=Cholesky(A)
	%A - HPD matrix
	%R - upper triangular matrix

	[m, n] = size(A);
	if m != n || any(any(A != A'))
		error('matrix is not symmetric')
	endif

	for k = 1: m
		if A(k, k) <= 0
      disp(A)
			error('matrix is not HPD')
		endif

		for j = k + 1: m
			A(j, j: m)=A(j , j: m) - A(k, j: m) * A(k,j ) / A(k, k);
		endfor

		A(k, k: m) = A(k, k: m) / sqrt(A(k, k));
	endfor

	R = triu(A);
endfunction

