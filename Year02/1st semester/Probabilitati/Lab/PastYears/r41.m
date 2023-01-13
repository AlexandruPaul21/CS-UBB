disp("cu returnare")

NS = 5000;

contorA = 0;
contorB = 0;
contorC = 0;
for i = 1:NS 
    smp = randsample("RRRRRRRRRRAAAVVVVV", 3, replacement = false);
    if smp(1) == 'A' && smp(2) == 'R' && smp(3) == 'V'
        contorA++;
    endif

    if not(ismember('V',smp))
        contorB++;
    endif

    if sum(smp=='V') == 1
        contorC++;
    endif
endfor

contorA/NS
contorB/NS
contorC/NS

disp("fara returnare")

NS = 5000;

contorA = 0;
contorB = 0;
contorC = 0;
for i = 1:NS 
    smp = randsample("RRRRRRRRRRAAAVVVVV", 3, replacement = true);
    if smp(1) == 'A' && smp(2) == 'R' && smp(3) == 'V'
        contorA++;
    endif

    if not(ismember('V',smp))
        contorB++;
    endif

    if sum(smp=='V') == 1
        contorC++;
    endif
endfor

contorA/NS
contorB/NS
contorC/NS