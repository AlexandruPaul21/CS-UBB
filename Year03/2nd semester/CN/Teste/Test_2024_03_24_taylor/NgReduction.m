function [r, kr] = NgReduction(x)

syms spi pip2 doippi xs y k f doi
nc = 360;

spi = vpa(pi, nc);
xs = vpa(x, nc);

doi = vpa(2, nc);

pip2 = vpa(spi / doi, nc);
doippi = vpa(doi / spi, nc);

y = vpa(xs * doippi, nc);

k = floor(y);
f = vpa(y - k, nc);

r = double(vpa(f * pip2, nc));
kr = double(k - floor(k/4) * 4);

end