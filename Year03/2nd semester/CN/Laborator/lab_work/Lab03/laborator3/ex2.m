syms x
format longg

for n=10:15
    hilbert=hilb(n);
    conditionarea=cond(hilbert);
    norma=norm(hilbert);
    raport=conditionarea/norma
end
