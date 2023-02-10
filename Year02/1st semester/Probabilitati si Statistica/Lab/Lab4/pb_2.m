m=1000;

p=sum(hygepdf(3:6,49,6,6));
x=geornd(p,1,m);

prob_estim=mean(x>=10)
prob_teor=1-sum(geopdf(0:9,p))