% s - evaluarile Spline
% [a, b, c, d] - coeficientii Spline

x=0:3; y=exp(x);
d=[exp(0), exp(3)];
t=[0.5 1.5 2.5];


% complete
[a1,b1,c1,d1] = coefSplineCubic(x,y,d,0);
spline_complete=evalSpline(x,[a1,b1,c1,d1],t)

% derivate secunde
[a2,b2,c2,d2] = coefSplineCubic(x,y,d,1);
spline_ds = evalSpline(x,[a2,b2,c2,d2],t)

% naturale
[a3,b3,c3,d3] = coefSplineCubic(x,y, [0, 0],2);
spline_nat = evalSpline(x,[a3,b3,c3,d3],t)

% deBoor
[a4,b4,c4,d4] = coefSplineCubic(x,y,[0, 0], 3);
spline_db = evalSpline(x,[a4,b4,c4,d4],t)


