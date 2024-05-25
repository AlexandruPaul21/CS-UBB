function val=aproximare_Cos_Taylor(x)

  %cos(x) = 1 - x^2/2! - x^4/4! - x^6/6! - ...

  val = 0;
  nextTerm = 1;
  n=1;

  while (val + nextTerm) ~= val
    val = val + nextTerm;
    nextTerm = - nextTerm * x^2 / (n*(n+1));
    n = n + 2;
  endwhile
endfunction
