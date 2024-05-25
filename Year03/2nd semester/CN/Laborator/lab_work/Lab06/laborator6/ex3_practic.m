x = [100, 113, 118, 121];
y = [10, 10.630145, 10.86278, 11];

t= 115;
aprox = Lagrange(x,y,t);
printf('Result: %.3f\n', aprox);
actual_value=10.7238
printf('Result: %.3f\n', actual_value);
