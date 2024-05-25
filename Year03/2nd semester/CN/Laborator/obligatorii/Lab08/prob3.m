p=[
   0.2,0.1;
   0.15,0.15;
   0.1,0.35;
   0,0.2;
   -0.1,0.35;
   -0.15,0.15;
    -0.2,0.1;
]
f=p(:,1);
g=p(:,2);

t=1:length(f);
sf=spline(t,f,1:0.1:length(t));
sg=spline(t,g,1:0.1:length(t));
plot(f,g,'o')
hold on
plot(sf,sg)
grid on
axis equal
xlabel('f')
ylabel('g')


