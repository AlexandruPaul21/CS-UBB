disp("a)")
NS = 1000;

prods = [];
contor = 0;
for i = 1:NS
    w = unifrnd(0,5,1,6);
    smp = randsample(w,3,replacement=true);
    prods = [prods;prod(smp)];
    if smp(1)+smp(2)>smp(3)
        contor++;
    endif
endfor

mean(prods)
contor/NS

disp("b)")
%prob ca evenimentul un numar din intervalul 2 4 sa aiba loc
p24 = unifcdf(4,0,5) - unifcdf(2,0,5)
binopdf(3,5,p24)