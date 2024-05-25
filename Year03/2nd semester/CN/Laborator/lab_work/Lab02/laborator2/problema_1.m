syms x
format longg

%E_MIN SI E_MAX
%eps
x=1;
while((1+x)~=1)
    tmp=x;
    x=x/2;
end

epsilon=tmp;
real_epsilon=eps(1);
fprintf('my eps: %e\n',epsilon);
fprintf('real eps: %e\n\n\n\n',real_epsilon);

%e_min si e_max
emin=-1022;
emax=1023;

computed_real_min=2.^emin;
computed_real_max=(2-epsilon)*2.^emax;

fprintf('my e_min: %e\n',computed_real_min);
fprintf('real e_min: %e\n',realmin);
fprintf('my e_max: %e\n',computed_real_max);
fprintf('real e_max: %e\n\n\n\n',realmax);





%NENORMALIZAT -> 
x=1;
while(x~=0)
    tmp=x;
    x=x/2;
end
nenormalizat=tmp;
epsilon_0=eps(0);
fprintf('cel mai mic numar normalizat calculat: %e\n', nenormalizat);
fprintf('cel mai mic numar normalizat masina: %e\n', epsilon_0);

%comparatie cu cel mai mic nr normalizat
fprintf('diff normalized si denormalized: %f\n',nenormalizat-epsilon_0);

