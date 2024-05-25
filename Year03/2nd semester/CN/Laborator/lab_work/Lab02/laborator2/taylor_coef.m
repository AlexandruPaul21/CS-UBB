function c=taylor_coef(f,n,a)
  c=sym( subs(diff(f, n), a) / factorial(n));
end