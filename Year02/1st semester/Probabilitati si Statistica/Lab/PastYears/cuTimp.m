disp("a)")
NS = 1000;

contor = 0;
sums = [];
for i = 1:NS
    v = unifrnd(0,10,1,5);
    smp = randsample(v,2);
    sums = [sums;sum(smp)];
    if abs(smp(1)-smp(2))>2
        contor++;
    endif
endfor

mean(sums)
contor/NS

disp("b)")
prob58 = unifcdf(8,0,10) - unifcdf(5,0,10);
binopdf(2,5,prob58)