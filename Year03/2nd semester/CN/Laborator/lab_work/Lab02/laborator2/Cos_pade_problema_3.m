function R=Cos_pade_problema_3(x)
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
    
    %initializare argumente  pt pade
    k=1;
    m=2;
    syms y
    f=cos(y);
    
    %pade matlab
    rez_pade_matlab = pade(cos(y),y,"Order",[1 2]);
    rez_pade_matlab = eval(subs(rez_pade_matlab,0));
    fprintf('rez pade matlab: %.10f\n',rez_pade_matlab);
  
    %calcul pade lab1
    if k == 0
        %calculeaz? seria Taylor pentru ordinul (m+1)
        R = val*taylor(f, 'order', m + 1);
    else
        %ini?ializeaz? vectorii pentru coeficien?i
        c = sym(zeros(1, k));
        r = sym(zeros(1, k));
        d = sym(zeros(1, k));

        %calculeaz? coeficien?ii pentru aproximare
        for i = 1:k
            c(i) = taylor_coef(f, m + i - 1, 0);
            r(i) = taylor_coef(f, m - i + 1, 0);
            d(i) = -taylor_coef(f, m + i, 0);
        end

        %construie?te matricea toeplitz ?i calculeaz? coeficien?ii
        C = toeplitz(c, r);
        b = inv(C) * d';
        b = [1; b];

        %calculeaz? coeficien?ii pentru aproximare
        a = sym(zeros(m + 1, 1));
        for j = 0:m
            for l = 0:min(j, k)
                a(j + 1) = a(j + 1) + taylor_coef(f, j - l,0) * b(l + 1);
            end
        end
        
        R = val*eval(eval(x.^(0:m)*a)/(x.^(0:k)*b));
        fprintf('rez pade a mea: %.10f\n',R);
    end
end
  
%Cos_pade_problema_3(pi/2);