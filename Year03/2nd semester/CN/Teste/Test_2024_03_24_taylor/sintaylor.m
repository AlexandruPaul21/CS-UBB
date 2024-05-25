function res = sintaylor(x)

res = 0;
t = x;
n = 1;

while res + t ~= res 
    res = res + t;
    t = -x^2/((n+1)*(n+2))*t;
    n = n + 2;
end

end