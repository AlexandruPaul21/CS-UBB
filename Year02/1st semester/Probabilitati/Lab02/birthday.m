function fr = birthday(NS = 10000, TOT = 23)
  contor = 0;
  for i = 1:NS
    b = randi(365, 1, TOT);
    w = sort(b);
    ok = 0;
    
    for i = 2:23 
      if w(i) == w(i-1)
        ok = 1;
      endif
    endfor
    
    contor = contor + ok;
    
  endfor
  
  fr = contor/NS;
