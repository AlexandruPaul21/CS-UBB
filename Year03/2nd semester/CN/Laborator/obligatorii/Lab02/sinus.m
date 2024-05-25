function y = sinus(x)

[r,k]=Ngreduction(x);

switch k
    case 0
        y = sintaylor(r);
    case 1
        y = costaylor(r);
    case 2
        y = -sintaylor(r);
    case 3
        y = -costaylor(r);
end