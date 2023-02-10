nr_sim=500;
clf; 
t=linspace(0,2*pi,360);
polar(t,4*ones(1,360),'w');
hold on;
[X,Y]=boxmuller(nr_sim);
plot(X,Y,'r*')
polar(t,0.5*ones(1,360),'b');
mean(sqrt(X.^2+Y.^2)<0.5)
1-exp(-1/8)
%P(sqrt(X^2+Y^2)<0.5)=P(sqrt(-2*log U1)<1/2)
%=P(U1>exp(-1/8))=1-exp(-1/8)

Z=normrnd(0,1,2,nr_sim);
plot(Z(1,:),Z(2,:),'c*')
mean(sqrt(Z(1,:).^2+Z(2,:).^2)<0.5)

