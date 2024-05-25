close all
f = @(x) 5*cos(x) + 2 * x - 3;
fd = @(x) -5*sin(x) + 2
fdd = @(x) -5*cos(x)


% generare coordonate pentru punctele pentru care se va estima spline-ul
x = linspace(0, 10, 5);
y = f(x);


figure;
hold on;
p1=plot(tg, f(tg), 'black');


% Tip 0 - Spline Complete
n=max(x);
tg=linspace(0,n,1000);
c = CubicSplinec(x,y,0,[fd(0), fd(10)]);
values = evalsplinec(x,c,tg);
p2=plot(tg, values, 'green');


% Tip 1 - Spline care reproduc derivate de ordinul al 2-lea
n=max(x);
tg=linspace(0,n,1000);
c = CubicSplinec(x,y,1, [fdd(0), fdd(10)]);
values = evalsplinec(x,c,tg);
p3=plot(tg, values, 'blue');


% Tip 2 - Spline naturale
n=max(x);
tg=linspace(0,n,1000);
c = CubicSplinec(x,y,2);
values = evalsplinec(x,c,tg);
p4=plot(tg, values, 'red');


% Tip 3 - Spline deBoor
n=max(x);
tg=linspace(0,n,1000);
c = CubicSplinec(x,y,3);
values = evalsplinec(x,c,tg);
p5=plot(tg, values, 'magenta')
plot(x, y, 'ko');


hold off;
legend([p1,p2,p3,p4,p5],'Functia originala', 'Spline Complet', 'Spline deriv2', 'Spline natural', 'Spline deBoor');
xlabel('X');
ylabel('Y');
title('Diverse tipuri de spline');


