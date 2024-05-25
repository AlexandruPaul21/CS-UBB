sinus_Taylor = [num2str(aproximare_Sin_Taylor(pi/3)), ' folosind seria Taylor'];
disp(sinus_Taylor)
disp(sin(pi/3));

cos_Taylor = [num2str(aproximare_Cos_Taylor(pi/3)), ' folosind seria Taylor'];
disp(cos_Taylor)
disp(cos(pi/3));

val = 10*pi;
%val = reducere_primul_cadran(val);
sinus_Taylor_10_pi = [num2str(aproximare_Sin_Taylor(val)), ' folosind seria Taylor'];
disp(sinus_Taylor_10_pi)
disp(sin(pi*10));
