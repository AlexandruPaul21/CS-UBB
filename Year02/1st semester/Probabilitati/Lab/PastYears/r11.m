disp('a)')
expcdf(3,4)

disp('b)')
NS = 1000;

x = exprnd(4,1,NS);
y = unifrnd(1,3,1,NS);

z = x+y;

medie = mean(z)

disp('c)')

sum(z<=4)/NS