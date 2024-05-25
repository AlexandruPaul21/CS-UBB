nodes = [0, 0.5, 1];
nodevals = exp(nodes);

t = 0 : 0.01 : 1;
lmf_values = Lagrange(nodes, nodevals, t);
f_values= exp(t);

plot(t, lmf_values, 'r');
hold on;
plot(t, f_values, 'b');
hold off;
legend('Lagrange Approximation', 'Real Exponential', 'Location', 'northeast');
title('Comparison of Lagrange Approximation with Exact Values');
xlabel('t');
ylabel('Function Value');
