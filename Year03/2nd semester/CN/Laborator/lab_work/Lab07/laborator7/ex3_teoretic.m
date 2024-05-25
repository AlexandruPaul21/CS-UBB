x1 = 0;
x2 = pi;
y1 = 0;
y2 = 0;
t1 = 1;
t2 = -1;

A = [x1^3 x1^2 x1 1; x2^3 x2^2 x2 1; 3*x1^2 2*x1 1 0; 3*x2^2 2*x2 1 0];
b = [y1 y2 t1 t2]';

%x = A^-1 * b
X = A\b;

a = X(1); b=X(2); c=X(3); d=X(4);
t = 0 : 0.04 : pi;

%scriem functia si derivata aflate
f = @(x)(a*x.^3 + b*x.^2 + c*x + d);
F = @(x)(3*a*x.^2 + 2*b*x + c);

y = f(t);
y_der= F(t);

figure();
plot(t, y);
hold on;
plot(t, y_der);

plot(x1, y1, 'b.', 'MarkerSize', 10);
plot(x2, y2, 'b.', 'MarkerSize', 10);
plot(x1, t1, 'r.', 'MarkerSize', 10);
plot(x2, t2, 'r.', 'MarkerSize', 10);
text(x1, y1, 'A', 'fontsize', 20);
text(x2, y2, 'B', 'fontsize', 20);
text(x1, t1, 'T1', 'fontsize', 20);
text(x2, t2, 'T2', 'fontsize', 20);
legend('Original polynomial', 'Derivative');  % Add legend
