function H = hermite(x, y, dy, xi)
% x - nodurile
% y - valoarea functiei in noduri
% dy - valoarea derivatei functiei in noduri
% xi - puncte de evaluat

  [z, Q] = calculDiferenteDizizate(x, y, dy);

  lx = length(xi);
  lz = length(z);

  for i = 1:lx
      x_diff = xi(i) - z;
      y(i) = [1, cumprod(x_diff(1:lz-1))]*Q';
  end
  [~, nrCol] = size(xi);
  H = y(1: nrCol);
end
