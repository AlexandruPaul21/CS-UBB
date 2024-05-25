function result = ngCos(x)

[r, k] = NgReduction(x);

switch k
    case 0
        result = costaylor(r);
    case 1
        result = -sintaylor(r);
    case 2
        result = -costaylor(r);
    case 3
        result = sintaylor(r);
end

end