disp("a)")
x = -2:0.01:5;
y = exppdf(x, 2);

figure;
plot(x, y);
xlabel('x');
ylabel('f(x)');
title('Functia de densitate');

figure;
y = expcdf(x, 2);
plot(x, y);
xlabel('x');
ylabel('F(x)');
title('Functia de repartitie');

NS = 10000; 
x = exprnd(2, 1, NS); 
mean_x = mean(x) 
std_x = std(x) 

disp("b)")
x = exprnd(2, 1, NS); 
p = sum(x > 0.7) / NS 

disp("c)")
p_teoretic = 1 - expcdf(0.7, 2)



