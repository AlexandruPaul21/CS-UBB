1;
function aprox = interpolare( valuesToAprox, f, m, nodes )
    considered = nodes(1 : m)
    consideredVals = f(considered)

    aprox = Lagrange(considered, consideredVals, valuesToAprox);
end
lagrange_aprox = interpolare([1.2,1.3], @exp, 3, [1.1 1.4 1.6])
actual_values = [exp(1.2), exp(1.3)]
