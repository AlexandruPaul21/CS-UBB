function [r,kr]=Ngreduction(x)
syms spi_ pip2 doippi xs y k f doi
nc = 345;

spi_ = vpa(pi,nc);
xs = vpa(x, 16);
doi = vpa(2,nc);
pip2 = vpa(spi_/doi,nc);
doippi = vpa(doi/spi_, nc);
y = vpa(xs*doippi, nc);
k = floor(y);
f = vpa(y-k, nc);
r = double(vpa(f*pip2, nc));
kr = double(k-floor(k/4)*4);