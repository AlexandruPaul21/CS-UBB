function out = square(NS = 500)
  clf; hold on; axis equal; grid on;
  
  rectangle('Position', [0,0,1,1])
  contor = 0;
  O = [0.5, 0.5];
  for i = 1:NS
    x = rand;
    y = rand;
    P = [x,y];
    if dist(O,P) < 0.5
      contor++;
      plot(x, y, 'r*')
    endif
  endfor
  
  out = contor/NS;
  