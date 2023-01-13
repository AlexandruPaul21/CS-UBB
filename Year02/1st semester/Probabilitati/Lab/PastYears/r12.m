disp("a)")
normcdf(1.5,1,1)

disp("b)")
NS = 5000;

times = normrnd(1,1,1,NS);

for i = 1:NS
    times(i) = abs(times(i));
    if times(i) > 2
        times(i) = 2 + exprnd(2,1,1);
    endif
endfor

mean(times)

disp("c)")

sum(times<=4)/NS
