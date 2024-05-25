function res = atantaylor(x)

res = 0;
t = x;
n = 1;

while res + t ~= res
    res = res + t;
    t = t * (-1)^n*x^2 * (2 * n - 1)/(2*n+1);
    n = n + 1;
end

end