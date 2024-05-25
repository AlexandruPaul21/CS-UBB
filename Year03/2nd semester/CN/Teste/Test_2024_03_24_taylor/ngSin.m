function result = ngSin(x)

[r, k] = NgReduction(x);

switch k
    case 0
        result = sintaylor(r);
    case 1
        result = costaylor(r);
    case 2
        result = -sintaylor(r);
    case 3
        result = -costaylor(r);
end

end