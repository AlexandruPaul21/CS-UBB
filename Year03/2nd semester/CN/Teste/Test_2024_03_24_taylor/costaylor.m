function res = costaylor(x)

res = 0;
t = 1;
n = 1;

while res + t ~= res 
    res = res + t;
    t = -x^2/(n*(n+1))*t;
    n = n + 2;
end

end