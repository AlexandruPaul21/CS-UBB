NS = 1000;

countC = 0;
countD = 0;
for i = 1:NS
    x1 = unifrnd(1,3,1,1);
    x2 = unifrnd(1,3,1,1);
    if x1>2 && x2<2
        countC++;
    endif

    if x1>2 || x2<2
        countD++;
    endif
endfor

disp("a)")
probC = countC/NS
probD = countD/NS

disp("b)")
probTeoC = (unifcdf(3,1,3) - unifcdf(2,1,3)) * unifcdf(2,1,3)
probTeoD = (unifcdf(3,1,3) - unifcdf(2,1,3)) + unifcdf(2,1,3) - unifpdf(2,1,3)/2

disp("c)")

Y = [];
for i = 1:NS
    x1 = unifrnd(1,3,1,1);
    x2 = unifrnd(1,3,1,1);
    
    Y = [Y;(x1*x2)];

endfor

ExPr = mean(Y)

%Y = min{x1,x2}*max{x1,x2} = x1*x2
%E(Y)=E(x1*x2) = E(x1)*E(x2)
%E(x1) = integrala(t*f(t),1,3)
%integrala de -inf la inf trece in integrala de la 1 la 3 deoarece avem Unif[1,3], inafara intervalului este 0
t = 1:3;
f = @(t) t.*unifpdf(t,1,3);
Ex1 = integral(f,1,3);
Ex2 = integral(f,1,3);
ExTeo = Ex1 * Ex2