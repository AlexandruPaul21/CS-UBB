x = [0, 1, 2];
y = [1, exp(1), exp(2)];
y_der = [1, exp(1), exp(2)];
xi = 0.25;

hermite_interpolated_value = hermite_interpolation(x, y, y_der, xi)
lagrange_interpolated_value = my_lagrange(x,y,xi)
real_value=exp(0.25)

eroare_hermite=real_value-hermite_interpolated_value
eroare_lagrange=real_value-lagrange_interpolated_value
