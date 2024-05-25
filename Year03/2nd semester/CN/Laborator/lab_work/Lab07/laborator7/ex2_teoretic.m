nodes = [0, 0.5, 1];
nodevals = exp(nodes);
nodevals_der=exp(nodes);

t = linspace(0,1,13);
hermite_values = arrayfun(@(x) hermite_interpolation(nodes, nodevals, nodevals_der, x), t);
f_values= exp(t);

plot(t, hermite_values, 'r');
hold on;
plot(t, f_values, 'b');
hold off;
legend('Lagrange Approximation', 'Real Exponential', 'Location', 'northeast');
title('Comparison of Lagrange Approximation with Exact Values');
xlabel('t');
ylabel('Function Value');

