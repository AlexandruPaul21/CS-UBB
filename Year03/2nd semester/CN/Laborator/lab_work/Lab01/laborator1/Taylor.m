
syms x;
format long
function [Tnf, Rnf] = Taylor(f, x, a, n)
  %TAYLOR - compute Taylor approximant and reminder
  %call [Tnf, Rnf] = Taylor(f, x, a, n)
  %f - symbolic function to approximate
  %x - function's indeterminate
  %a - approximation point
  %n - order of the approximant
  %Tnf - the approximant
  %Rnf - the reminder

  Tnf = 0;
  for i = 0: n - 1
    #diff(f,i)-derivata de ordin i a functiei f, subs inlocuieste x cu a
    Tnf = Tnf + (x - a) ^ i / factorial(i) * subs(diff(f, i), x, a);
  endfor

  syms theta
  Rnf = (x - a)^(n + 1) / factorial(n + 1) * subs(diff(f, x, n + 1), x, a + theta * (x - a));
endfunction

#Problema 5

f = log(1+x); % Example function
a = 0; % Approximation point
n = 8; % Order of the approximation
[Tnf, Rnf] = Taylor(f, x, a, n)
rez=eval(subs(Tnf,1))
formatted_rez = sprintf(['%.', num2str(5), 'f'], rez);
disp(formatted_rez);


%{
f = log((1+x)/(1-x)); % Example function
a = 0; % Approximation point
n = 8; % Order of the approximation
[Tnf, Rnf] = Taylor(f, x, a, n)
rez=eval(subs(Tnf,1/3))
formatted_rez = sprintf(['%.', num2str(5), 'f'], rez);
%}



#Problema 6
%{
f = atan(x); % Example function
a = 0; % Approximation point
n = 22; % Order of the approximation
[Tnf, Rnf] = Taylor(f, x, a, n)
rez=eval(subs(Tnf,pi/4))
formatted_rez = sprintf(['%.', num2str(5), 'f'], rez);
disp(formatted_rez);
%}


