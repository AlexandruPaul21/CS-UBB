format longg;

n=10000;
En=1/(n+1);
for i=n:-1:2
    En = (1-En)/i;
end

e_calculat = 1/En
e_real = exp(1)
