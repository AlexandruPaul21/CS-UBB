syms x;
f = sin(x);
pade = pade_aproximation(pi/3, f , 4 , 5);

disp(pade);
disp(sin(pi/3));

g = cos(x);
pade_cos = pade_aproximation(pi/3, g , 4 , 5);

disp(pade_cos);
disp(cos(pi/3));


