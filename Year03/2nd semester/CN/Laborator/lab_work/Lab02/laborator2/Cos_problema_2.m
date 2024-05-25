%aproximarea devine mai imprecisa pt ca punctul in care vrem sa calculam
%val cos/sin e mai departe de 0 (val in care e centrata functia taylor)
%facem reducere la primul cadran sa fim mai aproape de 0
function C=Cos_problema_2(x)
    %reducere la primul cadran
    x=mod(x, 2*pi);
    val=1;
    if x >= pi/2 && x<pi
        x=pi-x;
        val=-1;
    elseif x>=pi && x<3*pi/2
        val=-1;
        x=x-pi;
    elseif x>=3*pi/2
        x=2*pi-x;
    end

    


    %calculare valoare cu taylor
    x_la_putere=1;
    factorial=1;
    polinom=0;
    for i=1:10
        polinom=polinom+x_la_putere/factorial;
        x_la_putere=-x_la_putere*x^2;
        factorial=factorial*(2*i)*(2*i-1);
    end
    C=val*polinom;
end

% syms x
% Cos_problema_2(x)