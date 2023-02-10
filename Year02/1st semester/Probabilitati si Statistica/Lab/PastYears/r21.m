disp('a)')
%pentru S3
prob3 = 3/10;
prob4 = 4/10;
prob3s4 = prob3 + prob4

disp('b)')
NS = 1000;

time = [];
for i = 1:NS
    pc = randsample('1223334444', 1);
    if pc == '1'
        pcn = 1;
    endif

    if pc == '2'
        pcn = 2;
    endif

    if pc == '3'
        pcn = 3;
    endif

    if pc == '4'
        pcn = 4;
    endif
    time = [time;exprnd(pcn)];
endfor

mean(time)

disp('c)')
sum(time>=3)/NS