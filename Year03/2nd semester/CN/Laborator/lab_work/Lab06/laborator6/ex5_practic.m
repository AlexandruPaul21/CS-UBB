x=[0,pi/6,pi/4,pi/3,pi/2];
y_cos=[1,sqrt(3)/2, sqrt(2)/2, 1/2, 0];
y_sin=[0, 1/2, sqrt(2)/2, sqrt(3)/2, 1];

t=pi/36;
lagrange_cos= Lagrange(x,y_cos,t)
actual_cos=cos(t)

lagrange_sin= Lagrange(x,y_sin,t)
actual_sin=sin(t)
