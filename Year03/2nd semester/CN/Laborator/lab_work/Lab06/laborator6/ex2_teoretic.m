1;
function plot_lagrange_basis(x)
  % x - array-ul de coordonate
  % n - number of data points(degree=n-1)

  n = length(x);
  x_range = linspace(min(x) - 1, max(x) + 1, 1000); % plotting range

  % storage matrix for polynomial values
  L = zeros(n, length(x_range));

  % calculus for each Lagrange basis polynomial
  for i = 1:n
    L(i, :) = 1;
    for j = 1:n
      if i != j
        L(i, :) = L(i, :) .* (x_range - x(j)) / (x(i) - x(j));
      end
    end
  end

  % plotting
  hold on;
  for i = 1:n
    plot(x_range, L(i, :), 'DisplayName', sprintf('L_{%d}(x)', i));
  end

  legend('show');
  title('Lagrange Fundamental Polynomials');
  xlabel('x');
  ylabel('L_i(x)');
  grid on;
  hold off;
end


x = [1, 2, 4, 5];
plot_lagrange_basis(x);
