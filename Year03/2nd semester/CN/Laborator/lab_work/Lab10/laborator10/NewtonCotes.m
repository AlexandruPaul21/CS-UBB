function [ integration ] = NewtonCotes( f, a, b, n, mode )
h = (b - a)/n;
k = 0 : n;
x = a + h * k;
switch mode
    case 'trapez'
        integration = 0;
        for node = 1:n-1
            pieceInt = (h/2)*(f(x(node))+f(x(node+1)));
            integration = integration + pieceInt;
        end
    case 'simpson'
        integration = f(x(1)) + f(x(n));
        for node = 2 : n-1
            if(mod((node-1),2) == 0)
                integration = integration + 2 * f(x(node));
            end
            if(mod((node-1),2) ~=0 )
                integration = integration + 4 * f(x(node));
            end
        end
        integration = (h/3) * integration;
    case 'simpson3/8'
        integration = f(x(1)) + f(x(n));
        for node = 2 : n-1
            if(mod((node-1),3) == 0)
                integration = integration + 2 * f(x(node));
            end
            if(mod((node-1),3) ~=0 )
                integration = integration + 3 * f(x(node));
            end
        end
        integration = (3*h/8) * integration;
     case 'boole'
        integration = 7*(f(x(1)) + f(x(n)));
        for node = 2 : n-1
            if(mod((node-1),4) == 0 || mod((node-1),4) == 2)
                integration = integration + 32 * f(x(node));
            end
            if(mod((node-1),4) == 3)
                integration = integration + 12 * f(x(node));
            end
            if(mod((node-1),4) == 1 )
                integration = integration + 14 * f(x(node));
            end
        end
        integration = (2*h/45) * integration;
end