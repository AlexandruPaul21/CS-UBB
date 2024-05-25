function x = LupSolve(A,b)
	%LUPSOLVE - solution of an algebraic system by LUP decomposition

	[L, U, P] = lup(A);
	y = forwardsubst(L, P * b); %solve Ly=Pb
	x = backsubst(U, y);   %solve Ux=y for Ax=b
endfunction
