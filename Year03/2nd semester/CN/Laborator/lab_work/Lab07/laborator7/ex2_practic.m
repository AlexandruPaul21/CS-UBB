x = [0.30, 0.32, 0.35];
y = [0.29552, 0.31457,0.34290];
y_der = [0.95534, 0.94924, 0.93937];
xi = 0.34;
interpolated_value = hermite_interpolation(x, y, y_der, xi);
printf("sin(0.34) = %0.5f\n", interpolated_value);
eroare_3valori=sin(0.34)-interpolated_value



x = [0.30, 0.32, 0.33, 0.35];
y = [0.29552, 0.31457, 0.32404, 0.34290];
y_der = [0.95534, 0.94924, 0.94604, 0.93937];
xi = 0.34;
interpolated_value = hermite_interpolation(x, y, y_der, xi);
printf("sin(0.34) = %0.5f\n", interpolated_value);
eroare_4valori=sin(0.34)-interpolated_value

