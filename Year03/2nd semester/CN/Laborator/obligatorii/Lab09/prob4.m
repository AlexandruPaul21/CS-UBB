x = [1900:10:2010];
y = [75.995, 91.972, 105.710, 123.200, 131.670, 150.700, 179.320, 203.210, 226.510, 249.630, 281.420, 308.790];

p = polyfit(x,y,3);

xAprox = x(1):(x(length(x))-x(1))/100:x(length(x));
yAprox = polyval(p, xAprox);
title('Model polinomial de gradul 3');
plot(x, y, 'o', xAprox, yAprox, '-');

figure

cs=polyfit(x,log(y),1);
K=exp(cs(2));
lambda=cs(1);
fm=@(t) K*exp(lambda*t);
yAprox2 = fm(xAprox);

plot(x, y, 'o', xAprox, yAprox2, '-');

