function out = square2(NS = 1000)
  clf; hold on; axis equal; grid on;
  
  rectangle('Position', [0,0,1,1])
  contor = 0;
  O = [0.5, 0.5];
  for i = 1:NS
    x = rand;
    y = rand;
    P = [x,y];
    ok = 0;
    d1 = dist(O,P);
    
    if (d1 > dist(P, [0,0])) 
      ok = 1;
    endif
    
    if (d1 > dist(P, [0,1])) 
      ok = 1;
    endif
    
    if (d1 > dist(P, [1,0])) 
      ok = 1;
    endif
    
    if (d1 > dist(P, [1,1])) 
      ok = 1;
    endif
    
    if ok == 0
      contor++;
      plot(x, y, 'r*')
    endif
  endfor
  
  out = contor/NS;