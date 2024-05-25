x = [1, 2, 3, 4, 5];
y = [1, 4, 9, 16, 25];
t= [1.5, 2.5, 3.5, 4.5];

interpolated_values = my_lagrange(x, y, t);

disp('Interpolated values:');
disp(interpolated_values);



