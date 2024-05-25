function [ res ] = Dreptunghi( f, a, b, n )
h = (b - a) / n;
k = 0 : n;
x = a + h * k;

sum = 0;
for k = 2 : n
    sum = sum + f((x(k) + x(k-1)) / 2);
end

res = ((b - a) / n) * sum;

end
