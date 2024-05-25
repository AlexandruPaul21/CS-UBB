function val=aproximare_Sin_Taylor(x)

  %sin(x) = x - x^3/3! - x^5/5! - x^7/7! - ...

  val = 0;
  nextTerm = x;
  n = 1;

  %calculez termenii Taylor pana cand adaugarea unui nou termen nu produce vreo schimbare
  while (val + nextTerm) ~= val  %aprox egale
    val = val + nextTerm;
    nextTerm = (- nextTerm * (x^2)) / ((n + 1)*(n + 2));
    n = n + 2;
  endwhile

endfunction
