1;
function problema_4(x)
    hex = num2hex(x);
    if class(x) == 'double'
        bin = hexToBinaryVector(hex,64);
    else
        bin = hexToBinaryVector(hex,32);
    end

    %mereu o sa avem dim_1=1 linii si dim_2=32/64 coloane
    [dim_1, dim_2] = size(bin);

    if dim_2 == 64
        semn = bin(1)
        exponent = bin(2:12)
        mantisa = bin(13:64)
    elseif dim_2 == 32
        semn = bin(1)
        exponent = bin(2:9)
        mantisa = bin(10:32)
    end
    fprintf('semn %e\n',semn)
    fprintf('exponent %e\n',exponent)
    fprintf('mantisa %e\n',mantisa)
end

problema_4(double(5))
%problema_4(single(5))

