function out = square3(NS = 10000)
  clf; hold on; axis equal; grid on;
  
  rectangle('Position', [0,0,1,1])
  contor = 0;
  O = [0, 0];
  A = [1, 0];
  B = [1, 1];
  C = [0, 1];
  for i = 1:NS
    x = rand;
    y = rand;
    P = [x,y];
    d1 = dist(O,P);
    d2 = dist(A,P);
    d3 = dist(B,P);
    d4 = dist(C,P);
    
    ascutit = 0;
    dreptungh = 0;
    
    if d1^2 + d2^2 >= 1
      ascutit++;
    else
      dreptungh++;
    endif
    
    if d2^2 + d3^2 >= 1
      ascutit++;
    else
      dreptungh++;
    endif
    
    if d3^2 + d4^2 >= 1
      ascutit++;
    else
      dreptungh++;
    endif
    
    if d4^2 + d1^2 >= 1
      ascutit++;
    else
      dreptungh++;
    endif
   
    if d1^2 + d2^2 == 1
      ascutit = 0;
    endif
    if d3^2 + d2^2 == 1
      ascutit = 0;      
    endif
    if d3^2 + d4^2 == 1
      ascutit = 0;
    endif
    if d1^2 + d4^2 == 1
      ascutit = 0;      
    endif
    
    
    if ascutit ==2 && dreptungh ==2
      contor++;
      plot(x, y, 'r*')
    endif
  endfor
  
  out = contor/NS;