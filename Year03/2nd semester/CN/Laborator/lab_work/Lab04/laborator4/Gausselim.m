function x = Gausselim(A, b)
	%GAUSSELIM - Gaussian elimination with scaled colum pivoting
	%call x=Gausselim(A,b)
	%A - matrix, b- right hand side vector
	%x - solution

	[l, n] = size(A);
	x = zeros(size(b));
	A = [A, b]; %extended matrix
	piv = 1: n;

	%elimination
	for i = 1: n-1
    %u-max value, p-position in column segment
		[u, p] = max(abs(A(i: n, i))); %pivoting
    %row index
		p = p + i - 1;

    %no unique solution (singular matrix)
		if u == 0
			error('no unique solution')
		endif

		if p != i %row swapping
			piv([i, p]) = piv([p, i]);
		endif

		for j = i + 1: n
			m = A(piv(j), i) / A(piv(i), i);
			A(piv(j), i: n + 1) = A(piv(j), i: n + 1) - m * A(piv(i), i: n + 1);
		endfor
	endfor

  %disp(A);
  %disp(piv);

	%back substitution
	x(n) = A(piv(n), n + 1) / A(piv(n), n);
	for i = n - 1: -1: 1
		x(i) =(A(piv(i), n + 1) - A(piv(i), i + 1: n) * x(i + 1: n)) / A(piv(i), i);
	endfor
endfunction
