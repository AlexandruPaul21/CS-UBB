function y = cosinus(x)

[r,k]=Ngreduction(x);

switch k
    case 0
        y = costaylor(r);
    case 1
        y = -sintaylor(r);
    case 2
        y = -costaylor(r);
    case 3
        y = sintaylor(r);
end