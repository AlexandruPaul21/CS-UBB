close all

[x,y]=ginput();
n=length(x);
tn=0:n-1;
tg=linspace(0,n-1,1000);

c = CubicSplinec(tn,x,3)
xg = evalsplinec(tn,c,tg)
c = CubicSplinec(tn,y,3)
yg = evalsplinec(tn,c,tg)

plot(x,y,'o',xg,yg)
